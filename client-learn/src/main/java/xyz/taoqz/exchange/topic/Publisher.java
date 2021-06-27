package xyz.taoqz.exchange.topic;

import com.rabbitmq.client.Channel;
import xyz.taoqz.utils.RabbitMQUtil;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Publisher
 *
 * @author taoqz
 * @date 2021/6/26 15:35
 */
public class Publisher {

    public static final String exchange_name = "topic_exchange";

    public static void main(String[] args) throws Exception {
        final Channel channel = RabbitMQUtil.getChannel();
//        channel.exchangeDeclare(exchange_name, BuiltinExchangeType.TOPIC);

        final Map<String, String> map = new HashMap<>();
        map.put("quick.orange.rabbit", "被队列Q1Q2接收");
        map.put("lazy.orange.elephant", "被队列Q1Q2接收");
        map.put("quick.orange.fox", "被队列Q1接收");
        map.put("lazy.brown.fox", "被队列Q2接收");
        map.put("lazy.pink.rabbit", "虽然满足两个绑定但只被Q2队列（两个routingKey绑定到了同一个队列上）接收一次");
        map.put("quick.brown.fox", "不匹配任何绑定不会被任何队列接收会被丢弃");
        map.put("quick.orange.male.rabbit", "是四个单词不会匹配任何绑定会被丢弃");
        map.put("lazy.orange.male.rabbit", "是四个单词会匹配到队列Q2");

        for (Map.Entry<String, String> entry : map.entrySet()) {
            final String routingKey = entry.getKey();
            final String message = entry.getValue();
            channel.basicPublish(exchange_name, routingKey, null, message.getBytes(StandardCharsets.UTF_8));
        }
    }

}
