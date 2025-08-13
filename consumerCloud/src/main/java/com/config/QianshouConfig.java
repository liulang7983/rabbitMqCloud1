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
    public Queue qianshouQueue1(){
        return new Queue("qianshouQueue1",true);
    }
    @Bean
    public Queue qianshouQueue2(){
        //return new Queue("qianshouQueue2",true);
        Map map=new HashMap<>();
        //绑定死信交换机(要是已存在队列修改属性，需要删除重建，不然会一直报错)
        map.put("x-dead-letter-exchange","qianshouExchange3");
        //绑定的死信路由键(可以为不同的队列绑定同一个死信交换机(direct 或 topic 类型)，
        // 设置不同的死信路由键，之后再按照路由键分发到不同的异常处理队列)
        //map.put("x-dead-letter-routing-key",DEAD_LETTER_QUEUEA_ROUNTING_KEY_NAME);
        return QueueBuilder.durable("qianshouQueue2").withArguments(map).build();
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

    //声明队列
    @Bean
    public Queue qianshouQueue3(){
        return new Queue("qianshouQueue3",true);
    }
    //声明交换机
    @Bean
    public FanoutExchange qianshouExchange3(){
        return new FanoutExchange("qianshouExchange3",true,true);
    }

    //声明绑定关系
    @Bean
    public Binding fanoutExchangeA() {
        return BindingBuilder.bind(qianshouQueue3()).to(qianshouExchange3());
    }
}
