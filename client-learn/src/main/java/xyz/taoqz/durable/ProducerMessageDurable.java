package xyz.taoqz.durable;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import xyz.taoqz.utils.RabbitMQUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * ProducerMessageDurable
 *
 * @author taoqz
 * @date 2021/6/22 0:16
 */
public class ProducerMessageDurable {
    public static final String durable_queue_name = "durable_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        final Channel channel = RabbitMQUtil.getChannel();
        boolean durable = true;
        channel.queueDeclare(durable_queue_name, durable, false, false, null);
        String msg = "durable_msg——PERSISTENT_TEXT_PLAIN";
        // MessageProperties.PERSISTENT_TEXT_PLAIN 要求消息持久化
        channel.basicPublish("", durable_queue_name, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes(StandardCharsets.UTF_8));
    }
}
