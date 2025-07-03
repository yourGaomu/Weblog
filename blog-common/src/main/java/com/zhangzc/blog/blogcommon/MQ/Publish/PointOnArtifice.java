package com.zhangzc.blog.blogcommon.MQ.Publish;

import com.zhangzc.blog.blogcommon.enums.ResponseCodeEnum;
import com.zhangzc.blog.blogcommon.exception.exceptionClasses.BizException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PointOnArtifice {

    private final RabbitTemplate rabbitTemplate;


    public void publishPointOnArtifice(Long artId) {
        try {
            if (artId == null) {
                log.error("发布点赞消息失败：文章ID为空");
                return;
            }
            String msg = String.valueOf(artId);
            rabbitTemplate.convertAndSend("blog.pointArtifice", "point", msg);
            log.info("增加记录，文章ID：{}", artId);
        } catch (Exception e) {
            log.error("增加记录，文章ID失败：{}", artId, e);
            throw new BizException(ResponseCodeEnum.SPEND_MEG_FAILED);
        }
    }
}
