package xyz.taoqz.ack;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import xyz.taoqz.utils.RabbitMQUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Consumer01
 *
 * @author taoqz
 * @date 2021/6/21 23:30
 */
public class Consumer01 {

    public static final String queue_name = "ack_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        final Channel channel = RabbitMQUtil.getChannel();

        System.out.println("消费者1开始接收消息。。。");
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("process...");
                System.out.println(new String(message.getBody()));
            // 消息唯一Id   是否批量应答
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            System.out.println("end");
        };

//        channel.basicQos(1);
        // 手动应答
        boolean autoAck = false;
        channel.basicConsume(queue_name, autoAck, deliverCallback, (consumerTag) -> {
            System.out.println("消息取消发送");
        });
    }

}
