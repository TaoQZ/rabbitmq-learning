package xyz.taoqz.bootlearn.rabbitmqlistener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * TtlListener
 *
 * @author taoqz
 * @date 2021/6/27 20:04
 */
@Slf4j
@Component
public class TtlListener {

    @RabbitListener(queues = "QD")
    public void receiveD(Message message, Channel channel) {
        final String msg = new String(message.getBody());
        log.info("当前时间：{}，接收到消息：{}", new Date().toString(), msg);
    }

}
