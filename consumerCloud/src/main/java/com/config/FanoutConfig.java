package com.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ming.li
 * @date 2023/3/21 10:35
 */
@Configuration
public class FanoutConfig {

    @Bean
    public Queue fanoutQueueTest(){
        return new Queue("fanoutTest",true);
    }
    /*此时不能定义 new FanoutExchange("liming_test",true,true)
    * 因为队列已经在客户端或者是在producer定义了，那他的属性是不鞥修改的，
    * 此时只需要new FanoutExchange("liming_test")就可以
    * */

    @Bean
    public FanoutExchange fanoutExchangeTest(){
        return new FanoutExchange("liming_test");
    }

    @Bean
    public Binding fanoutBindingTest(){
        return BindingBuilder.bind(fanoutQueueTest()).to(fanoutExchangeTest());
    }

    @Bean
    public Queue fanoutQueueTest2(){
        return new Queue("fanoutQueueTest2",true);
    }

    /*订阅模式可以在生产者定义交换机，以及其durable和autoDelete属性，消费者这两个属性是要保持一致
     *绑定具体的队列可以在消费者实现
     * */
    @Bean
    public FanoutExchange fanoutExchangeTest2(){
        return new FanoutExchange("fanout_exchange_test2",true,true);
    }

    @Bean
    public Binding fanoutBindingTest2(){
        return BindingBuilder.bind(fanoutQueueTest2()).to(fanoutExchangeTest2());
    }
}
