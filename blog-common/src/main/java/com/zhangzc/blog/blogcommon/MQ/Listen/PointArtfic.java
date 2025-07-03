package com.zhangzc.blog.blogcommon.MQ.Listen;


import com.zhangzc.blog.blogcommon.MQ.MQService.RabbitMQService;
import com.zhangzc.blog.blogcommon.Utils.R;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PointArtfic {

    private final RabbitMQService rabbitMQService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "point.onArtfic", declare = "false"),//不会持久化
            exchange = @Exchange(name = "blog.pointArtifice", type = ExchangeTypes.TOPIC, declare = "false")//topic的交换机,也不会持久化
    ))
    public R listen(String meg) {
        Boolean b = rabbitMQService.addPointByArtID(meg);
        if (b) {
            R.Success("增加成功",meg);
        }
        return R.Faile("增加失败");
    }
}
