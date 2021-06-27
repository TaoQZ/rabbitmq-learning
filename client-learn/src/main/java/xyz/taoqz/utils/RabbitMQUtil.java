package xyz.taoqz.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * RabbitMQUtil
 *
 * @author taoqz
 * @date 2021/6/21 22:11
 */
public class RabbitMQUtil {

    public static Channel getChannel() throws IOException, TimeoutException {
        final ConnectionFactory factory =
            new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setUsername("admin");
        factory.setPassword("admin");
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();
        return channel;
    }

}
