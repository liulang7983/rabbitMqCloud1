package com.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ming.li
 * @date 2023/2/28 22:08
 */
@Configuration
public class WorkqueuesConfig {

    @Bean
    public Queue workqueuesQueue(){
        return new Queue("workqueues",true);
    }
}
