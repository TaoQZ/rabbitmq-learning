package xyz.taoqz.ack;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import xyz.taoqz.utils.RabbitMQUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Consumer02
 *
 * @author taoqz
 * @date 2021/6/21 23:31
 */
public class Consumer02 {

    public static final String queue_name = "ack_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        final Channel channel = RabbitMQUtil.getChannel();

        System.out.println("消费者2开始接收消息。。。");
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("process...");
            try {
                TimeUnit.SECONDS.sleep(20);
                System.out.println(new String(message.getBody()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 消息唯一Id
            // 是否批量应答
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            System.out.println("end");
        };

        // 设置不公平分发
//        channel.basicQos(1);
        // 预取值
        int prefetchCount = 5;
        channel.basicQos(prefetchCount);
        // 手动应答
        boolean autoAck = false;
        channel.basicConsume(queue_name, autoAck, deliverCallback, (consumerTag) -> {
            System.out.println("消息取消发送");
        });
    }

}
