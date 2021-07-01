package xyz.taoqz.publishconfirm;

import com.rabbitmq.client.Channel;
import org.apache.commons.lang.time.StopWatch;
import xyz.taoqz.utils.RabbitMQUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * producer
 *
 * @author taoqz
 * @date 2021/6/22 23:01
 */
public class producer {

    public static final int queue_count = 1000;

    public static void main(String[] args) throws IOException, InterruptedException, TimeoutException {
        publishMessageSingle();
//        publishMessageBatch();
//        publishMessageAsync();
    }

    // 单个确认发布
    public static void publishMessageSingle() throws IOException, TimeoutException, InterruptedException {
        final Channel channel = RabbitMQUtil.getChannel();
        // 开启消息发布确认
        channel.confirmSelect();
        final String queueName = UUID.randomUUID().toString();
//        channel.queueDeclare(queueName, false, false, false, null);

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < queue_count; i++) {
            final String msg = UUID.randomUUID().toString();
//            channel.basicPublish("", queueName, null, msg.getBytes(StandardCharsets.UTF_8));
            // 如果没有开启消息确认,报错会在下行发生 (交换机sss不存在)
            channel.basicPublish("sss", "qqqq", null, msg.getBytes(StandardCharsets.UTF_8));
//            (x.exchange交换机存在,qqqq队列不存在时也能发送成功,但消息无法路由会被直接丢弃,但是生产者并不知道没有路由,此时需学习发布确认高级篇)
//            channel.basicPublish("x.exchange", "qqqq", null, msg.getBytes(StandardCharsets.UTF_8));
            // 单个消息确认
            // 如果开启消息确认,报错会在下行发生,报错内容一样,但是异常名称不同
            final boolean flag = channel.waitForConfirms();
            if (flag) {
                System.out.println("消息：" + msg + "发布成功");
            } else {
                System.out.println("消息：" + msg + "发送失败");
            }
        }
        stopWatch.stop();
        System.out.println(stopWatch.getTime());
    }

    // 批量确认发布
    public static void publishMessageBatch() throws IOException, TimeoutException, InterruptedException {
        final Channel channel = RabbitMQUtil.getChannel();
        // 开启消息发布确认
        channel.confirmSelect();
        final String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, false, false, false, null);

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < queue_count; i++) {
            final String msg = UUID.randomUUID().toString();
            channel.basicPublish("", queueName, null, msg.getBytes(StandardCharsets.UTF_8));
            if (i % 100 == 0) {
                // 每100次 消息确认一次
                channel.waitForConfirms();
            }
        }
        stopWatch.stop();
        System.out.println(stopWatch.getTime());
    }

    // 异步确认发布
    public static void publishMessageAsync() throws IOException, TimeoutException, InterruptedException {
        final Channel channel = RabbitMQUtil.getChannel();
        // 开启消息发布确认
        channel.confirmSelect();
        final String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, false, false, false, null);

        /**
         * 消息发布成功回调
         * 消息发布失败回调
         *      deliveryTag： 消息的标记
         *      multiple： 是否未批量确认
         */
        channel.addConfirmListener(
            (deliveryTag, multiple) -> {
                System.out.println("消息发送成功：" + deliveryTag + "   " + multiple);
            }, (deliveryTag, multiple) -> {
                System.out.println("消息发送失败：" + deliveryTag + "   " + multiple);
            }
        );

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < queue_count; i++) {
            final String msg = UUID.randomUUID().toString();
            channel.basicPublish("", queueName, null, msg.getBytes(StandardCharsets.UTF_8));
        }
        stopWatch.stop();
        System.out.println(stopWatch.getTime());
    }

}
