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
public class Consumer2 {

    public static final String exchange_name = "direct_exchange";

    public static void main(String[] args) throws Exception{
        final Channel channel = RabbitMQUtil.getChannel();
        channel.exchangeDeclare(exchange_name, BuiltinExchangeType.DIRECT);

        final String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName,exchange_name,"c2");
        channel.basicConsume(queueName,true,((consumerTag, message) -> {
            System.out.println("消费者2接收到消息："+new String(message.getBody()));
        }),(consumerTag, sig) -> {});
    }
}
