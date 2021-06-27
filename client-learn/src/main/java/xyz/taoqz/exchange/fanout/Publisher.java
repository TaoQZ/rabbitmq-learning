package xyz.taoqz.exchange.fanout;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import xyz.taoqz.utils.RabbitMQUtil;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Publisher
 *
 * @author taoqz
 * @date 2021/6/26 14:42
 */
public class Publisher {

    public static final String exchange_name = "fanout_exchange";

    public static void main(String[] args) throws Exception {
        final Channel channel = RabbitMQUtil.getChannel();
        // fanout(扇出)：将所有发送到交换机的消息路由到所有与它绑定的queue中
        channel.exchangeDeclare(exchange_name, BuiltinExchangeType.FANOUT);

        final Scanner scanner = new Scanner(System.in);
        while (true) {
            final String msg = scanner.nextLine();
            channel.basicPublish(exchange_name, "", null, msg.getBytes(StandardCharsets.UTF_8));
        }
    }

}
