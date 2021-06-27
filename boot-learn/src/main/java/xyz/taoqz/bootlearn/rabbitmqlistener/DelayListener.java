package xyz.taoqz.bootlearn.rabbitmqlistener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * DelayListener
 *
 * @author taoqz
 * @date 2021/6/27 21:16
 */
@Slf4j
@Component
public class DelayListener {

    private final String delay_queue = "delay.queue";

    @RabbitListener(queues = delay_queue)
    public void receiveDelay(Message message, Channel channel) {
        final String msg = new String(message.getBody());
        log.info("当前时间：{}，接收到消息：{}", new Date().toString(), msg);
    }

}
