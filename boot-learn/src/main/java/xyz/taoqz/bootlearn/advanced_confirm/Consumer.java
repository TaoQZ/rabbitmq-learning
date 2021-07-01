package xyz.taoqz.bootlearn.advanced_confirm;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Consumer
 *
 * @author taoqz
 * @date 2021/7/1 9:49
 */
@Slf4j
@Component
public class Consumer {

    public static final String queue_name = "confirm.queue";

    @RabbitListener(queues = queue_name)
    public void receive(Message message, Channel channel) {
        log.info("接收到队列：" + queue_name + " 的消息：" + new String(message.getBody()));
//        log.in(new String(message.getBody()));
    }

}
