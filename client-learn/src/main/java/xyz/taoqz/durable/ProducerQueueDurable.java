package xyz.taoqz.durable;

import com.rabbitmq.client.Channel;
import xyz.taoqz.utils.RabbitMQUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * ProducerQueueDurable
 *
 * @author taoqz
 * @date 2021/6/22 0:05
 */
public class ProducerQueueDurable {

//    如果队列已经在指定的vhost消息队列中存在，就会出现此异常，需要删掉重新建队列
//    Caused by: com.rabbitmq.client.ShutdownSignalException: channel error; protocol method: #method<channel.close>(reply-code=406, reply-text=PRECONDITION_FAILED - inequivalent arg 'durable' for queue 'hello' in vhost '/': received 'true' but current is 'false', class-id=50, method-id=10)
    public static final String queue_name = "hello";
    public static final String durable_queue_name = "durable_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        final Channel channel = RabbitMQUtil.getChannel();
        boolean durable = true;
        channel.queueDeclare(durable_queue_name, durable, false, false, null);
        String msg = "durable_msg";
        channel.basicPublish("", durable_queue_name, null, msg.getBytes(StandardCharsets.UTF_8));
    }
}
