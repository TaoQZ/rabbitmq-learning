package xyz.taoqz.bootlearn.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * RabbitMQController
 *
 * @author taoqz
 * @date 2021/6/27 19:37
 */
@Slf4j
@RestController
@RequestMapping("/rabbitmq")
public class RabbitMQController {

    private final String x_exchange = "x.exchange";
    private final String xa_routingKey = "XA";
    private final String xb_routingKey = "XB";
    private final String xc_routingKey = "XC";

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/sendTtlMsg/{msg}")
    public String sendTtlMsg(@PathVariable String msg) {
        rabbitTemplate.convertAndSend(x_exchange, xa_routingKey, "消息来自ttl为10的队列QA：" + msg);
        rabbitTemplate.convertAndSend(x_exchange, xb_routingKey, "消息来自ttl为30的队列QA：" + msg);
        log.info("发送消息，时间：{}", new Date().toString());
        return "sendTtl OK!";
    }

    @GetMapping("/sendExpirationMsg/{msg}/{ttl}")
    public String sendExpirationMsg(@PathVariable String msg, @PathVariable String ttl) {
        rabbitTemplate.convertAndSend(x_exchange, xc_routingKey, "消息来自ttl为10的队列QA：" + msg, message -> {
            message.getMessageProperties().setExpiration(ttl);
            return message;
        });
        log.info("发送消息，ttl:{}，当前时间：{}", ttl, new Date().toString());
        return "sendExpirationMsg OK!";
    }

    private final String delay_exchange = "delay.exchange";
    private final String delay_routingKey = "delay.routingKey";

    @GetMapping("/sendDelayMsg/{msg}/{delayTime}")
    public String sendDelayMsg(@PathVariable String msg, @PathVariable Integer delayTime) {
        rabbitTemplate.convertAndSend(delay_exchange, delay_routingKey, "消息来自delayTime为" + delayTime + "的队列QA：" + msg, message -> {
            message.getMessageProperties().setDelay(delayTime);
            return message;
        });
        log.info("发送消息，delayTime:{}，当前时间：{}", delayTime, new Date().toString());
        return "sendExpirationMsg OK!";
    }
}
