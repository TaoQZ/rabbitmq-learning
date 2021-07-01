package xyz.taoqz.bootlearn.advanced_confirm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * MyConfirmCallback
 *
 * @author taoqz
 * @date 2021/7/1 11:00
 */
@Slf4j
@Component
public class MyConfirmCallback implements RabbitTemplate.ConfirmCallback {
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        final String id = correlationData.getId();
        if (ack) {
            log.info("交换机已接收到id为：" + id + "的消息");
        } else {
            log.info("交换机接收不到id为：" + id + "的消息，原因为：" + cause);
        }
    }
}
