package com.config;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ming.li
 * @date 2023/5/15 18:32
 */
@Configuration
public class SiXinConfig {

    @Bean
    public Queue createIn(){
        //return new Queue("sixinIn",true);
        Map map=new HashMap<>();
        //绑定死信交换机
        map.put("x-dead-letter-exchange","sixinExchangeOut");
        //绑定的死信路由键
        //map.put("x-dead-letter-routing-key",DEAD_LETTER_QUEUEA_ROUNTING_KEY_NAME);
        return QueueBuilder.durable("sixinIn").withArguments(map).build();
    }

    @Bean
    public Queue createOut(){
        return new Queue("sixinOut",true);
    }
    @Bean
    public FanoutExchange createExchangeIn(){
        return new FanoutExchange("sixinExchangeIn");
    }
    @Bean
    public FanoutExchange createExchangeOut(){
        return new FanoutExchange("sixinExchangeOut");
    }
    @Bean
    public Binding bindingIn(){
        return BindingBuilder.bind(createIn()).to(createExchangeIn());
    }
    @Bean
    public Binding bindingOut(){
        return BindingBuilder.bind(createOut()).to(createExchangeOut());
    }
}
