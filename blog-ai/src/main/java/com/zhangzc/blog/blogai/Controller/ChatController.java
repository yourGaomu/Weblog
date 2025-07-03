package com.zhangzc.blog.blogai.Controller;

import com.zhangzc.blog.blogai.Exception.IsEmptyForChatCount;
import com.zhangzc.blog.blogai.Exception.IsEmptyForQN;
import com.zhangzc.blog.blogai.Exception.IsNoRole;
import com.zhangzc.blog.blogai.Pojo.Vo.AiMessage;
import com.zhangzc.blog.blogai.Pojo.domain.TAi;
import com.zhangzc.blog.blogai.AiService.ChatCommonService;
import com.zhangzc.blog.blogai.AiService.ChatStreamService;
import com.zhangzc.blog.blogai.Service.TAiService;
import com.zhangzc.blog.blogai.Threds.ForDecTaiThred;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;


@RequestMapping
@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatStreamService chatStreamService;
    private final ChatCommonService chatCommonService;
    private final TAiService tAiService;


    @PostMapping(value = "/chat/", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chat(@RequestBody AiMessage aiMessage, HttpServletRequest request) throws Exception {
        //获取请求的ip地址
        String ipAddress = request.getRemoteAddr();
        System.out.println(ipAddress);
        //判断该用户是否存在于表中并且未被封禁并且次数是否用尽
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
        //如果是清楚俩天记录的请求，应该使用另外一个链接

        if (one.getChatCount()==0) {
            throw new IsEmptyForChatCount("对不起你的对话次数已经用尽了","500");
        }
        //全部符合，则会进行允许访问ai提问



        //先启动另外一个线程减去对话次数
        ForDecTaiThred forDecTaiThred = new ForDecTaiThred(tAiService,one.getId().toString());
        forDecTaiThred.call();
        //返回用户的询问
        return chatStreamService.chat(String.valueOf(one.getId()),message);
    }
}
