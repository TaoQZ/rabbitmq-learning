package xyz.taoqz.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * Producer
 *
 * @author taoqz
 * @date 2021/6/21 21:37
 */
public class Producer {

    public static final String queue_name = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setUsername("admin");
        factory.setPassword("admin");

        // 创建连接
        final Connection connection = factory.newConnection();
        // 获取信道
        final Channel channel = connection.createChannel();
        // 创建队列
        /**
         * 队列名称
         * 是否持久化 默认false
         * 队列是否进行消息共享（默认只有一个消费者可以消费）
         * 是否自动删除，最后一个消费者断开连接以后，该队列是否自动删除
         * 其他参数
         */
        channel.queueDeclare(queue_name, false, false, false, null);

        String message = "HelloWorld！";
        /**
         * 交换机
         * 路由key值，本次为队列名称
         * 其他参数
         * 消息体
         */
        channel.basicPublish("", queue_name, null, message.getBytes(StandardCharsets.UTF_8));
    }

}
