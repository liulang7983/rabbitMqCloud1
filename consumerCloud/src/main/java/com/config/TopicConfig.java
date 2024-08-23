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
public class TopicConfig {

    @Bean
    public Queue topicQueueTest(){
        return new Queue("topicD",true);
    }
}
