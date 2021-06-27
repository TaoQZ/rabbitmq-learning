package xyz.taoqz.helloworld;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Consumer
 *
 * @author taoqz
 * @date 2021/6/21 21:52
 */
// 消费者
public class Consumer {

    public static String queue_name = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {

        final ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setUsername("admin");
        factory.setPassword("admin");

        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();


        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println(new String(message.getBody()));
        };

        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("消息被中断");
        };

        /**
         * 消费哪个队列
         * 消费成功是否自动应答 true 自动应答
         * 消费者未成功消费的回调
         * 消费者取消消费回调
         */
        channel.basicConsume(queue_name, true, deliverCallback, cancelCallback);

    }

}
