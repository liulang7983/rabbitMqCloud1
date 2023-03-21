package com.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ming.li
 * @date 2023/3/21 13:43
 */
@Configuration
public class QianshouConfig {

    @Bean
    public Queue qianshouQueue1(){
        return new Queue("qianshouQueue1",true);
    }
    @Bean
    public Queue qianshouQueue2(){
        return new Queue("qianshouQueue2",true);
    }

    @Bean
    public FanoutExchange qianshouExchange1(){
        return new FanoutExchange("qianshouExchange1",true,true);
    }

    @Bean
    public FanoutExchange qianshouExchange2(){
        return new FanoutExchange("qianshouExchange2",true,true);
    }

    @Bean
    public Binding qianshouBinding1(){
        return BindingBuilder.bind(qianshouQueue1()).to(qianshouExchange1());
    }

    @Bean
    public Binding qianshouBinding2(){
        return BindingBuilder.bind(qianshouQueue2()).to(qianshouExchange2());
    }
}
