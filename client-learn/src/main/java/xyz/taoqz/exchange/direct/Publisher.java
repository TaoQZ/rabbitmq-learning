package xyz.taoqz.exchange.direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import xyz.taoqz.utils.RabbitMQUtil;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Publisher
 *
 * @author taoqz
 * @date 2021/6/26 15:13
 */
public class Publisher {

    public static final String exchange_name = "direct_exchange";

    public static void main(String[] args) throws Exception {
        final Channel channel = RabbitMQUtil.getChannel();
        channel.exchangeDeclare(exchange_name, BuiltinExchangeType.DIRECT);
        final Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("请输入routingkey");
            final String routingKey = scanner.nextLine();
            System.out.println("请输入msg");
            final String msg = scanner.nextLine();
            channel.basicPublish(exchange_name, routingKey, null, msg.getBytes(StandardCharsets.UTF_8));
        }
    }

}
