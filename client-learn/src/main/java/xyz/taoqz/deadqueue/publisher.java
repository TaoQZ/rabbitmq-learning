package xyz.taoqz.deadqueue;

import com.rabbitmq.client.Channel;
import xyz.taoqz.utils.RabbitMQUtil;

import java.nio.charset.StandardCharsets;

/**
 * publisher
 *
 * @author taoqz
 * @date 2021/6/26 21:07
 */
public class publisher {

    public static final String normal_exchange = "normal_exchange";
    public static final String normal_routingKey = "normal_routingKey";

    public static void main(String[] args) throws Exception {
        final Channel channel = RabbitMQUtil.getChannel();
        // 设置消息的TTL时间 10s build的单位为ms
//        final AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().expiration("10000").build();
        for (int i = 0; i < 10; i++) {
            String message = "info" + i;
//            channel.basicPublish(normal_exchange, normal_routingKey, properties, message.getBytes(StandardCharsets.UTF_8));
            channel.basicPublish(normal_exchange, normal_routingKey, null, message.getBytes(StandardCharsets.UTF_8));
        }
    }

}
