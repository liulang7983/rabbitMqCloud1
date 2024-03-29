package com.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ming.li
 * @date 2023/2/28 22:08
 */
@Configuration
public class FanoutConfig {
    
    @Bean
    public Queue fanoutQueueA(){
        return new Queue("fanoutA",true);
    }

    @Bean
    public Queue fanoutQueueB(){
        return new Queue("fanoutB",true);
    }

    @Bean
    public FanoutExchange createFanoutExchange(){
        return new FanoutExchange("fanoutExchange",true,true);
    }
    @Bean
    public Binding fanoutExchangeA(){
        return BindingBuilder.bind(fanoutQueueA()).to(createFanoutExchange());
    }

    @Bean
    public Binding fanoutExchangeB(){
        return BindingBuilder.bind(fanoutQueueB()).to(createFanoutExchange());
    }


    /*订阅模式可以在生产者定义交换机，以及其durable和autoDelete属性，消费者这两个属性是要保持一致
     *绑定具体的队列可以在消费者实现
     * */
    @Bean
    public FanoutExchange fanoutExchangeTest2(){
        return new FanoutExchange("fanout_exchange_test2",true,true);
    }
}
