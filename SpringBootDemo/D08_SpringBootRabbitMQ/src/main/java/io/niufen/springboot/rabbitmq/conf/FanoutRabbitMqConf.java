package io.niufen.springboot.rabbitmq.conf;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * fanout 分列
 * Fanout Exchange
 * Fanout 就是我们熟悉的广播模式或者订阅模式，
 * 给 Fanout 交换机发送消息，
 * 绑定了这个交换机的所有队列都收到这个消息。
 * @author haijun.zhang
 * @date 2020/5/20
 * @time 21:55
 */
@Configuration
public class FanoutRabbitMqConf {

    public final static String FANOUT_QUEUE_A = "fanout.A";
    public final static String FANOUT_QUEUE_B = "fanout.B";
    public final static String FANOUT_QUEUE_C = "fanout.C";
    public final static String FANOUT_EXCHANGE_NAME = "fanout.E";

    @Bean(name = "aQueue")
    public Queue aQueue(){
        return new Queue(FANOUT_QUEUE_A);
    }
    @Bean(name = "bQueue")
    public Queue bQueue(){
        return new Queue(FANOUT_QUEUE_B);
    }
    @Bean(name = "cQueue")
    public Queue cQueue(){
        return new Queue(FANOUT_QUEUE_C);
    }

    @Bean(name = "fanoutExchange")
    FanoutExchange fanoutExchange(){
        return new FanoutExchange(FANOUT_EXCHANGE_NAME);
    }

    /**
     * 这里使用了 A、B、C 三个队列绑定到 Fanout 交换机上面，
     * 发送端的 routing_key 写任何字符都会被忽略：
     */
    @Bean
    Binding bindingExchangeA(@Qualifier("aQueue") Queue aQueue, @Qualifier("fanoutExchange") FanoutExchange fanoutExchange){
        return BindingBuilder.bind(aQueue).to(fanoutExchange);
    }
    @Bean
    Binding bindingExchangeB(@Qualifier("bQueue") Queue bQueue, @Qualifier("fanoutExchange") FanoutExchange fanoutExchange){
        return BindingBuilder.bind(bQueue).to(fanoutExchange);
    }
    @Bean
    Binding bindingExchangeC(@Qualifier("cQueue") Queue cQueue, @Qualifier("fanoutExchange") FanoutExchange fanoutExchange){
        return BindingBuilder.bind(cQueue).to(fanoutExchange);
    }
}
