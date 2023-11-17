package com.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ming.li
 * @date 2023/11/17 21:04
 */
@Configuration
public class TtlConfig {
    //创建队列
    @Bean
    public Queue create(){
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-message-ttl", 10000);
        Queue queue = new Queue("ttl_queue",true,false,false,arguments);
        return queue;
    }
    //床架交换机
    @Bean
    public FanoutExchange createttlExchange(){
        return new FanoutExchange("liming_exchange_ttl",true,true);
    }
    @Bean
    public Binding fanoutExchangeTtl(){
        return BindingBuilder.bind(create()).to(createttlExchange());
    }
}
