package com.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Binding;

/**
 * @author ming.li
 * @date 2023/3/1 10:57
 */
@Configuration
public class TopicConfig {
    //绑定键
    public final static String man = "topic.man";
    public final static String woman = "topic.woman";

    @Bean
    public Queue firstQueue() {
        return new Queue("topicA");
    }

    @Bean
    public Queue secondQueue() {
        return new Queue("topicB");
    }
    @Bean
    public Queue QueueD() {
        return new Queue("topicD");
    }
    @Bean
    TopicExchange exchange() {
        return new TopicExchange("topicExchange");
    }


    //将firstQueue和topicExchange绑定,而且绑定的键值为topic.man
    //这样只要是消息携带的路由键是topic.man,才会分发到该队列
    @Bean
    Binding bindingExchangeMessage() {
        return BindingBuilder.bind(firstQueue()).to(exchange()).with(man);
    }

    //将secondQueue和topicExchange绑定,而且绑定的键值为用上通配路由键规则topic.#
    // 这样只要是消息携带的路由键是以topic.开头,都会分发到该队列
    //没有指定规则则是和direct一样，全值匹配
    @Bean
    Binding bindingExchangeMessage2() {
        return BindingBuilder.bind(secondQueue()).to(exchange()).with("topic.#");
    }

    //此topic.*时只能绑定topic.xx这种，如归是topic.xx.xx则不会匹配
    //没有指定规则则是和direct一样，全值匹配
    @Bean
    Binding bindingExchangeMessage3() {
        return BindingBuilder.bind(QueueD()).to(exchange()).with("topic.*");
    }

}