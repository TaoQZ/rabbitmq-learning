package xyz.taoqz.ack;

import com.rabbitmq.client.Channel;
import xyz.taoqz.utils.RabbitMQUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * Producer
 *
 * @author taoqz
 * @date 2021/6/21 23:30
 */
public class Producer {

    public static final String queue_name = "ack_queue";

    public static void main(String[] args) throws IOException, TimeoutException {

        final Channel channel = RabbitMQUtil.getChannel();
        channel.queueDeclare(queue_name, false, false, false, null);

        final Scanner scanner = new Scanner(System.in);
        while (true) {
            final String msg = scanner.nextLine();
            channel.basicPublish("", queue_name, null, msg.getBytes(StandardCharsets.UTF_8));
        }

    }

}
