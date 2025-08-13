package com.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ming.li
 * @date 2023/3/21 13:43
 */
@Configuration
public class QianshouConfig {

    @Bean
    public FanoutExchange qianshouExchange1(){
        return new FanoutExchange("qianshouExchange1",true,true);
    }

    @Bean
    public FanoutExchange qianshouExchange2(){
        return new FanoutExchange("qianshouExchange2",true,true);
    }
}
