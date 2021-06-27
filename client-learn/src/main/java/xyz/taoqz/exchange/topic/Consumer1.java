package xyz.taoqz.exchange.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import xyz.taoqz.utils.RabbitMQUtil;

/**
 * Consumer1
 *
 * @author taoqz
 * @date 2021/6/26 19:11
 */
public class Consumer1 {

    public static final String excange_name = "topic_exchange";
    public static final String routingKey = "*.orange.*";
    public static final String queue_name = "Q1";

    public static void main(String[] args) throws Exception {
        // 获取信道
        final Channel channel = RabbitMQUtil.getChannel();
        // 声明并创建队列
        channel.queueDeclare(queue_name, false, false, false, null);
        // 声明并创建交换机
        channel.exchangeDeclare(excange_name, BuiltinExchangeType.TOPIC);
        // 将队列绑定至交换机
        channel.queueBind(queue_name, excange_name, routingKey);

        // 接收消息
        channel.basicConsume(queue_name, true, (consumerTag, message) -> {
            System.out.println("队列Q1接收到消息：" + new String(message.getBody()) + "所属routingKey：" + message.getEnvelope().getRoutingKey());
        }, (consumerTag) -> {
        });
    }

}
