package xyz.taoqz.bootlearn.advanced_confirm;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ConfirmConfig
 *
 * @author taoqz
 * @date 2021/7/1 9:15
 */
@Configuration
public class ConfirmConfig {

    // 交换机
    private static final String exchange_name = "confirm.exchange";
    // 队列
    private static final String queue_name = "confirm.queue";
    // 路由
    private static final String routing_key_name = "confirm.routing.key";

    @Bean("confirmExchange")
    public DirectExchange confirmExchange(){
        return new DirectExchange(exchange_name);
    }

    @Bean("confirmQueue")
    public Queue confirmQueue(){
        return QueueBuilder.durable(queue_name).build();
    }

    @Bean
    public Binding binding(@Qualifier("confirmExchange") DirectExchange confirmExchange,
                           @Qualifier("confirmQueue") Queue confirmQueue){
        return BindingBuilder.bind(confirmQueue).to(confirmExchange).with(routing_key_name);
    }

}
