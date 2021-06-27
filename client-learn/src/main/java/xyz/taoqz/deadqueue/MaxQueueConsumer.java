package xyz.taoqz.deadqueue;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import xyz.taoqz.utils.RabbitMQUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * MaxQueueConsumer
 *
 * @author taoqz
 * @date 2021/6/26 21:37
 */
public class MaxQueueConsumer {

    // 普通交换机和队列
    public static final String normal_exchange = "normal_exchange";
    public static final String normal_routingKey = "normal_routingKey";
    public static final String normal_queue_name = "normal_queue";

    // 接收死信的交换机和队列
    public static final String dead_exchange = "dead_exchange";
    public static final String dead_routingKey = "dead_routingKey";
    public static final String dead_queue_name = "dead_queue";

    public static void main(String[] args) throws Exception{
        final Channel channel = RabbitMQUtil.getChannel();

        // 声明并创建普通和死信交换机
        channel.exchangeDeclare(normal_exchange, BuiltinExchangeType.DIRECT);
//        channel.exchangeDeclare(dead_exchange, BuiltinExchangeType.DIRECT);

        // 声明并创建普通和死信队列
//        channel.queueDeclare(dead_queue_name, false, false, false, null);

        // 由于参数改变需要先删除原来的队列，再新建
//        channel.queueDelete(normal_queue_name);
        final Map<String, Object> arguments = new HashMap<>();
        // 为正常队列设置死信交换机
        arguments.put("x-dead-letter-exchange", dead_exchange);
        // 为正常队列设置死信routingKey
        arguments.put("x-dead-letter-routing-key", dead_routingKey);
        // 为正常队列设置queue最大长度 一定要是数字类型 5 。。。
        arguments.put("x-max-length", 5);
        channel.queueDeclare(normal_queue_name, false, false, false, arguments);

        // 绑定队列
//        channel.queueBind(dead_queue_name, dead_exchange, dead_routingKey);
        channel.queueBind(normal_queue_name, normal_exchange, normal_routingKey);

        channel.basicConsume(normal_queue_name, true, normal_routingKey, (consumerTag, message) -> {
            System.out.println("消费者接收消息：" + new String(message.getBody()));
        }, (consumer) -> {
        });
    }

}
