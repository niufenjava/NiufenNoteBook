package io.niufen.springboot.rabbitmq.conf;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Topic Exchange
 * topic 是 RabbitMQ 中最灵活的一种方式，可以根据 routing_key 自由的绑定不同的队列
 * 首先对 topic 规则配置，这里使用两个队列来测试
 * @author haijun.zhang
 * @date 2020/5/20
 * @time 21:55
 */
@Configuration
public class TopicRabbitMqConf {

    public final static String TOPIC_QUEUE_ONE = "topic.queue.one";
    public final static String TOPIC_QUEUE_TWO = "topic.queue.two";
    public final static String TOPIC_QUEUE_ALL = "topic.queue.all";
    public final static String TOPIC_QUEUE_START = "topic.queue.#";
    public final static String TOPIC_EXCHANGE_NAME = "topic.exchange";

    @Bean(name = "queueMessageOne")
    public Queue queueMessageOne(){
        return new Queue(TOPIC_QUEUE_ONE);
    }

    @Bean(name = "queueMessageTwo")
    public Queue queueMessageTwo(){
        return new Queue(TOPIC_QUEUE_TWO);
    }

    @Bean(name = "queueMessageAll")
    public Queue queueMessageAll(){
        return new Queue(TOPIC_QUEUE_ALL);
    }

    @Bean(name = "topicExchange")
    public TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

    @Bean(name = "topicExchangeOther")
    public TopicExchange topicExchangeOther(){
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

    /**
     * 将 TOPIC_QUEUE_ONE 绑定到一个交换机 topic.exchange
     */
    @Bean
    Binding bindingExchangeOnTopicOne(@Qualifier("queueMessageOne") Queue queueMessageOne, @Qualifier("topicExchange") TopicExchange topicExchange){
        return BindingBuilder.bind(queueMessageOne).to(topicExchange).with(TOPIC_QUEUE_ONE);
    }

    /**
     * 将 TOPIC_QUEUE_TOPIC_QUEUE_TWO 绑定到一个交换机 topic.exchange
     */
    @Bean
    Binding bindingExchangeOnTopicStar(@Qualifier("queueMessageTwo")Queue queue, @Qualifier("topicExchange") TopicExchange topicExchange){
        return BindingBuilder.bind(queue).to(topicExchange).with(TOPIC_QUEUE_START);
    }
}
