package com.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ming.li
 * @date 2023/2/28 22:08
 */
@Configuration
public class DirectConfig {
    
  /*  @Bean
    public Queue directQueueA(){
        return new Queue("directA",true);
    }

    @Bean
    public Queue directQueueB(){
        return new Queue("directB",true);
    }

    @Bean
    public DirectExchange createDirectExchange(){
        return new DirectExchange("directExchange",true,true);
    }
    @Bean
    public Binding directExchangeA(){
        return BindingBuilder.bind(directQueueA()).to(createDirectExchange()).with("directA");
    }

    @Bean
    public Binding directExchangeB(){
        return BindingBuilder.bind(directQueueB()).to(createDirectExchange()).with("directB");
    }*/
}
