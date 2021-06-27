package xyz.taoqz.exchange.fanout;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import xyz.taoqz.utils.RabbitMQUtil;

/**
 * Consumer1
 *
 * @author taoqz
 * @date 2021/6/26 14:45
 */
public class Consumer1 {

    public static final String exchange_name = "fanout_exchange";

    public static void main(String[] args) throws Exception {
        final Channel channel = RabbitMQUtil.getChannel();
        channel.exchangeDeclare(exchange_name, BuiltinExchangeType.FANOUT);

        // 创建临时队列，消费者断开连接后队列自动删除
        final String queueName = channel.queueDeclare().getQueue();

        // 将队列绑定至交换机
        channel.queueBind(queueName,exchange_name,"");

        channel.basicConsume(queueName,true,(consumerTag,msg) -> {
            System.out.println("消费者1接收到消息："+new String(msg.getBody()));
        },(consumerTag)-> {});
    }

}
