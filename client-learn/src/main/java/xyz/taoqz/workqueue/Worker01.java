package xyz.taoqz.workqueue;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import xyz.taoqz.utils.RabbitMQUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Work01
 *
 * @author taoqz
 * @date 2021/6/21 22:13
 */
// 工作线程1
public class Worker01 {

    public static final String queue_name = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        final Channel channel = RabbitMQUtil.getChannel();
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("工作线程1，接收消息：" + new String(message.getBody()));
        };
        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println("消息取消消费");
        };
        System.out.println("工作线程1 success...");
        channel.basicConsume(queue_name, true, deliverCallback, cancelCallback);
    }

}
