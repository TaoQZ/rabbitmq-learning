package xyz.taoqz.deadqueue;

import com.rabbitmq.client.Channel;
import xyz.taoqz.utils.RabbitMQUtil;

/**
 * DeadConsumer
 *
 * @author taoqz
 * @date 2021/6/26 21:34
 */
public class DeadConsumer {

    // 接收死信的交换机和队列
    public static final String dead_exchange = "dead_exchange";
    public static final String dead_routingKey = "dead_routingKey";
    public static final String dead_queue_name = "dead_queue";

    public static void main(String[] args) throws Exception {
        final Channel channel = RabbitMQUtil.getChannel();
        channel.basicConsume(dead_queue_name, true, dead_routingKey, (consumerTag, message) -> {
            System.out.println("死信队列——消费者接收消息：" + new String(message.getBody()));
        }, (consumer) -> {
        });

    }

}
