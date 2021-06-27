package xyz.taoqz.deadqueue;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import xyz.taoqz.utils.RabbitMQUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * RejectConsumer
 *
 * @author taoqz
 * @date 2021/6/26 21:59
 */
public class RejectConsumer {

    // 普通交换机和队列
    public static final String normal_exchange = "normal_exchange";
    public static final String normal_routingKey = "normal_routingKey";
    public static final String normal_queue_name = "normal_queue";

    // 接收死信的交换机和队列
    public static final String dead_exchange = "dead_exchange";
    public static final String dead_routingKey = "dead_routingKey";
    public static final String dead_queue_name = "dead_queue";

    public static void main(String[] args) throws Exception {
        final Channel channel = RabbitMQUtil.getChannel();

        // 声明并创建普通和死信交换机
        channel.exchangeDeclare(normal_exchange, BuiltinExchangeType.DIRECT);

        // 由于参数改变需要先删除原来的队列，再新建
        channel.queueDelete(normal_queue_name);
        final Map<String, Object> arguments = new HashMap<>();
        // 为正常队列设置死信交换机
        arguments.put("x-dead-letter-exchange", dead_exchange);
        // 为正常队列设置死信routingKey
        arguments.put("x-dead-letter-routing-key", dead_routingKey);
        channel.queueDeclare(normal_queue_name, false, false, false, arguments);

        // 绑定队列
        channel.queueBind(normal_queue_name, normal_exchange, normal_routingKey);

        boolean autoAck = false;
        channel.basicConsume(normal_queue_name, autoAck, normal_routingKey, (consumerTag, message) -> {
            final String msg = new String(message.getBody());
            if (msg.equals("info4")) {
                System.out.println("消费者接收消息：" + msg + "并拒绝接收消息");
                // requeue 是否重新放回正常队列，选择否
                channel.basicReject(message.getEnvelope().getDeliveryTag(), false);
            } else {
                System.out.println("消费者接收消息：" + msg);
                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            }
        }, (consumer) -> {
        });
    }

}
