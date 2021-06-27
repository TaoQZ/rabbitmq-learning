package xyz.taoqz.workqueue;

import com.rabbitmq.client.Channel;
import xyz.taoqz.utils.RabbitMQUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * WorkConsumer
 *
 * @author taoqz
 * @date 2021/6/21 22:17
 */
public class WorkProducer {

    public static final String queue_name = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {

        final Channel channel = RabbitMQUtil.getChannel();
        channel.queueDeclare(queue_name, false, false, false, null);
        for (int i = 0; i < 20; i++) {
            String message = "HelloWorld！==" + i;

            /**
             * 交换机
             * 路由key值，本次为队列名称
             * 其他参数
             * 消息体
             */
            channel.basicPublish("", queue_name, null, message.getBytes(StandardCharsets.UTF_8));
        }
    }

}
