package xyz.taoqz.bootlearn.advanced_confirm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * MyReturnCallback
 *
 * @author taoqz
 * @date 2021/7/1 11:30
 */
@Slf4j
@Component
public class MyReturnCallback implements RabbitTemplate.ReturnsCallback {
    @Override
    public void returnedMessage(ReturnedMessage returned) {
        final String exchange = returned.getExchange();
        final String message = new String(returned.getMessage().getBody());
        final String routingKey = returned.getRoutingKey();
        final int replyCode = returned.getReplyCode();
        final String replyText = returned.getReplyText();
        log.info("交换机：" + exchange + " routingkey：" + routingKey + " 消息：" + message + " replyCode：" + replyCode + " replyText： " + replyText);
    }
}
