package xyz.taoqz.bootlearn.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQConfig
 *
 * @author taoqz
 * @date 2021/6/27 19:37
 */
@Configuration
public class RabbitMQConfig {

    private final String x_exchange = "x.exchange";
    private final String qa_queue = "QA";
    private final String qb_queue = "QB";
    private final String qc_queue = "QC";
    private final String xa_routingKey = "XA";
    private final String xb_routingKey = "XB";
    private final String xc_routingKey = "XC";

    private final String dead_y_exchange = "dead.y.exchange";
    private final String dead_qd_queue = "QD";
    private final String dead_yd_routingKey = "YD";

    @Bean("xExchange")
    public DirectExchange xExchange() {
        return new DirectExchange(x_exchange);
    }

    @Bean("QA")
    public Queue QA() {
        return QueueBuilder.durable(qa_queue)
            .deadLetterExchange(dead_y_exchange)
            .deadLetterRoutingKey(dead_yd_routingKey)
            .ttl(10000)
            .build();
    }

    @Bean("QB")
    public Queue QB() {
        return QueueBuilder.durable(qb_queue)
            .deadLetterExchange(dead_y_exchange)
            .deadLetterRoutingKey(dead_yd_routingKey)
            .ttl(30000)
            .build();
    }

    @Bean("QC")
    public Queue QC() {
        return QueueBuilder.durable(qc_queue)
            .deadLetterExchange(dead_y_exchange)
            .deadLetterRoutingKey(dead_yd_routingKey)
            .build();
    }

    @Bean
    public Binding QABindingXExchange(@Qualifier("QA") Queue qa,
                                      @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(qa).to(xExchange).with(xa_routingKey);
    }

    @Bean
    public Binding QBBindingXExchange(@Qualifier("QB") Queue qb,
                                      @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(qb).to(xExchange).with(xb_routingKey);
    }

    @Bean
    public Binding QCBindingXExchange(@Qualifier("QC") Queue qc,
                                      @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(qc).to(xExchange).with(xc_routingKey);
    }

    @Bean("yExchange")
    public DirectExchange yExchange() {
        return new DirectExchange(dead_y_exchange);
    }

    @Bean("QD")
    public Queue QD() {
        return QueueBuilder.durable(dead_qd_queue)
            .build();
    }

    @Bean
    public Binding QDBindingXExchange(@Qualifier("QD") Queue qd,
                                      @Qualifier("yExchange") DirectExchange yExchange){
        return BindingBuilder.bind(qd).to(yExchange).with(dead_yd_routingKey);
    }

}
