package com.zhangzc.blog.blogai.Controller;

import com.zhangzc.blog.blogai.Exception.IsEmptyForChatCount;
import com.zhangzc.blog.blogai.Exception.IsEmptyForQN;
import com.zhangzc.blog.blogai.Exception.IsNoRole;
import com.zhangzc.blog.blogai.Pojo.Vo.AiMessage;
import com.zhangzc.blog.blogai.Pojo.domain.TAi;
import com.zhangzc.blog.blogai.AiService.ChatStreamService;
import com.zhangzc.blog.blogai.Service.TAiService;
import com.zhangzc.blog.blogai.Threds.ForDecTaiThred;
import com.zhangzc.blog.blogai.Utils.RedisUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;


@RequestMapping
@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatStreamService chatStreamService;
    private final TAiService tAiService;
    private final RedisUtil redisUtil;


    @PostMapping(value = "/chat/", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chat(@RequestBody AiMessage aiMessage) throws Exception {
        String qq_num = aiMessage.getQq();
        String message = aiMessage.getMessage();

        if (qq_num.isEmpty()) {
            throw new IsEmptyForQN("请去评论区登录一下哦", "500");
        }
        TAi one = tAiService.lambdaQuery()
                .eq(TAi::getQq, qq_num)
                .eq(TAi::getIsBanned, 0)
                .eq(TAi::getRole, 1)
                .one();

        if (one == null) {
            throw new IsNoRole("你没有权限", "500");
        }

        if (one.getChatCount() == 0) {
            throw new IsEmptyForChatCount("对不起你的对话次数已经用尽了", "500");
        }

        //先启动另外一个线程减去对话次数
        ForDecTaiThred forDecTaiThred = new ForDecTaiThred(tAiService, one.getId().toString());
        forDecTaiThred.call();
        //返回用户的询问
        return chatStreamService.chat(String.valueOf(one.getId()), message);
    }

    @DeleteMapping("/chat/delete")
    public void chatDelete(@RequestBody AiMessage aiMessage) throws Exception {
        String message = aiMessage.getMessage();
        String qq = aiMessage.getQq();
        if (message.contains("请求删除聊天记录")) {
            TAi one = tAiService.lambdaQuery().eq(TAi::getQq, qq).one();
            if (one != null) {
                Integer id = one.getId();
                if (id != null) {
                    redisUtil.del(id.toString());
                }
            }
        }
    }
}
