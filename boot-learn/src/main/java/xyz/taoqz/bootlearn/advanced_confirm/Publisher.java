package xyz.taoqz.bootlearn.advanced_confirm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

/**
 * Publisher
 *
 * @author taoqz
 * @date 2021/7/1 9:48
 */
@Slf4j
@Component
@Controller
@RequestMapping("/publish")
public class Publisher {

    // 交换机
    public static final String exchange_name = "confirm.exchange";
    // 队列
    public static final String queue_name = "confirm.queue";
    // 路由
    public static final String routing_key_name = "confirm.routing.key";

    private final RabbitTemplate rabbitTemplate;
    private final MyConfirmCallback myConfirmCallback;
    private final MyReturnCallback returnCallback;

    public Publisher(RabbitTemplate rabbitTemplate, MyConfirmCallback myConfirmCallback, MyReturnCallback returnCallback) {
        this.rabbitTemplate = rabbitTemplate;
        this.myConfirmCallback = myConfirmCallback;
        this.returnCallback = returnCallback;
    }

    @PostConstruct
    public void init(){
        // 注入消息发送至交换机的消息确认回调
        rabbitTemplate.setConfirmCallback(myConfirmCallback);

        /**
         * true: 交换机无法将消息路由时，会将该消息返回给生产者
         * false: 交换机无法将消息路由时，直接丢弃消息
         */
        rabbitTemplate.setMandatory(true);
        // 注入消息无法被消费时的回调（回退消息）
        rabbitTemplate.setReturnsCallback(returnCallback);
    }

    @ResponseBody
    @RequestMapping("/sendMsg/{msg}")
    public String publish(@PathVariable String msg) {
        // 消息无法路由
        final CorrelationData correlationData1 = new CorrelationData("1");
        rabbitTemplate.convertAndSend(exchange_name, routing_key_name+"ss", msg.getBytes(StandardCharsets.UTF_8), correlationData1);

        // 交换机不存在，接收不到消息
        final CorrelationData correlationData2 = new CorrelationData("2");
        rabbitTemplate.convertAndSend(exchange_name + "sdf", routing_key_name, msg.getBytes(StandardCharsets.UTF_8), correlationData2);
        return "OK!";
    }

//    @RabbitListener(queues = queue_name,concurrency = "1")
//    public void q1(Message message, Channel channel) throws IOException {
//        System.out.println("q1：" + new String(message.getBody())+"   "+channel);
//    }
//
//    @RabbitListener(queues = queue_name,concurrency = "5")
//    public void q2(Message message, Channel channel) throws IOException {
//        System.out.println("q2：" + new String(message.getBody())+"  "+channel);
//    }

//    交换机已接收到id为：1的消息
//    Shutdown Signal: channel error; protocol method: #method<channel.close>(reply-code=404, reply-text=NOT_FOUND - no exchange 'confirm.exchangesdf' in vhost '/', class-id=60, method-id=40)
//    交换机接收不到id为：2的消息，原因为：channel error; protocol method: #method<channel.close>(reply-code=404, reply-text=NOT_FOUND - no exchange 'confirm.exchangesdf' in vhost '/', class-id=60, method-id=40)
//    接收到队列：confirm.queue 的消息：zzzzzzzzzzzzz


//    交换机：confirm.exchange routingkey：confirm.routing.keyss 消息：zzzzzzzzzzzzz replyCode：312 replyText： NO_ROUTE
//    交换机已接收到id为：1的消息
//    Shutdown Signal: channel error; protocol method: #method<channel.close>(reply-code=404, reply-text=NOT_FOUND - no exchange 'confirm.exchangesdf' in vhost '/', class-id=60, method-id=40)
//    交换机接收不到id为：2的消息，原因为：channel error; protocol method: #method<channel.close>(reply-code=404, reply-text=NOT_FOUND - no exchange 'confirm.exchangesdf' in vhost '/', class-id=60, method-id=40)


}
