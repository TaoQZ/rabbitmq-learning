package xyz.taoqz.exchange.direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import xyz.taoqz.utils.RabbitMQUtil;

/**
 * Consumer1
 *
 * @author taoqz
 * @date 2021/6/26 15:16
 */
public class Consumer1 {

    public static final String exchange_name = "direct_exchange";

    public static void main(String[] args) throws Exception {
        final Channel channel = RabbitMQUtil.getChannel();
        channel.exchangeDeclare(exchange_name, BuiltinExchangeType.DIRECT);

        final String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, exchange_name, "c1");
        System.out.println(queueName);

        // 多个队列绑定同一个routingKey
        final String queueName2 = channel.queueDeclare().getQueue();
        channel.queueBind(queueName2, exchange_name, "c1");
        System.out.println(queueName2);

        // 一个队列绑定多个routingKey
//        channel.queueBind(queueName, exchange_name, "c3");

        channel.basicConsume(queueName, true, ((consumerTag, message) -> {
            System.out.println("消费者1接收到消息：" + new String(message.getBody()));
        }), (consumerTag, sig) -> {
        });

        channel.basicConsume(queueName2, true, ((consumerTag, message) -> {
            System.out.println("消费者1接收到消息_：" + queueName2 + new String(message.getBody()));
        }), (consumerTag, sig) -> {
        });
    }
}
