package xyz.taoqz.bootlearn.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * DelayQueueConfig
 *
 * @author taoqz
 * @date 2021/6/27 21:05
 */
@Configuration
public class DelayQueueConfig {

    private final String delay_exchange = "delay.exchange";
    private final String delay_queue = "delay.queue";
    private final String delay_routingKey = "delay.routingKey";

    /**
     * 自定义交换机
     *
     * @return
     */
    @Bean("delayExchange")
    public CustomExchange delayExchange() {
        final Map<String, Object> args = new HashMap<>();
        // 添加标头,否则报错：Invalid argument, 'x-delayed-type' must be an existing exchange type
        args.put("x-delayed-type", "direct");
        return new CustomExchange(delay_exchange, "x-delayed-message", true, false, args);
    }

    @Bean("delayQueue")
    public Queue delayQueue() {
        return new Queue(delay_queue);
    }

    @Bean
    public Binding delayBinding(@Qualifier("delayExchange") CustomExchange delayExchange,
                                @Qualifier("delayQueue") Queue delayQueue) {
        return BindingBuilder.bind(delayQueue).to(delayExchange).with(delay_routingKey).noargs();
    }

}
