package com.config;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


/**
 * @Author ming.li
 * @Date 2024/8/23 15:40
 * @Version 1.0
 */
@Configuration
public class YanshiConfig {

    @Bean
    public FanoutExchange createExchangeYanshiIn(){
        return new FanoutExchange("exchangeYanshiin");
    }

    @Bean
    public FanoutExchange createExchangeYanshiOut(){
        return new FanoutExchange("exchangeYanshiout");
    }
    @Bean
    public FanoutExchange createExchangeYanshiOut1(){
        return new FanoutExchange("exchangeYanshiout1");
    }
    @Bean
    public Queue createQueueIn(){
        Map<String, Object> args = new HashMap<>(3);
        //声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", "exchangeYanshiout");
        //声明当前队列的死信路由 key
        //args.put("x-dead-letter-routing-key", "XA");
        //声明队列的 TTL
        args.put("x-message-ttl", 10000);
        return QueueBuilder.durable("yanshiin").withArguments(args).build();
    }
    @Bean
    public Queue createQueueIn1(){
        Map<String, Object> args = new HashMap<>(3);
        //声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", "exchangeYanshiout1");
        //声明当前队列的死信路由 key
        //args.put("x-dead-letter-routing-key", "XA");
        //声明队列的 TTL
        args.put("x-message-ttl", 10000);
        return QueueBuilder.durable("yanshiin1").withArguments(args).build();
    }
    @Bean
    public Queue createQueueOut(){
       return new Queue("yanshiout");
    }
    @Bean
    public Queue createQueueOut1(){
        return new Queue("yanshiout1");
    }
    @Bean
    public Binding bindingYanshiIn(){
        return BindingBuilder.bind(createQueueIn()).to(createExchangeYanshiIn());
    }
    @Bean
    public Binding bindingYanshiIn1(){
        return BindingBuilder.bind(createQueueIn1()).to(createExchangeYanshiIn());
    }
    @Bean
    public Binding bindingYanshiOut(){
        return BindingBuilder.bind(createQueueOut()).to(createExchangeYanshiOut());
    }
    @Bean
    public Binding bindingYanshiOut1(){
        return BindingBuilder.bind(createQueueOut1()).to(createExchangeYanshiOut1());
    }
}
