package com.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ming.li
 * @date 2023/11/18 10:16
 */
@Configuration
public class TtlAndSiXinConfig {

    // 声明用于延时的队列
    @Bean("ttlExchange")
    public DirectExchange ttlExchange() {
        return new DirectExchange("ttlExchange",true,true);
    }

    // 声明 死信队列交换机
    @Bean("deadExchange")
    public DirectExchange deadExchange() {
        return new DirectExchange("deadExchange",true,true);
    }

    //声明ttl队列 ttlQueueA ttl 为 10s 并绑定到对应的死信交换机以及对应的路由键，再有死信交换机去路由到匹配的队列上
    @Bean("ttlQueueA")
    public Queue ttlQueueA() {
        Map<String, Object> args = new HashMap<>(3);
        //声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", "deadExchange");
        //声明当前队列的死信路由 key
        args.put("x-dead-letter-routing-key", "deadQueueA");
        //声明队列的 TTL
        args.put("x-message-ttl", 10000);
        //return new Queue("ttlQueueA",true,false,false,args);
       return QueueBuilder.durable("ttlQueueA").withArguments(args).build();
    }

    // 声明ttl队列 ttlQueueA 绑定 ttlExchange 交换机
    @Bean
    public Binding queueaBindingttlA(@Qualifier("ttlQueueA") Queue ttlQueueA,
                                  @Qualifier("ttlExchange") DirectExchange ttlExchange) {
        return BindingBuilder.bind(ttlQueueA).to(ttlExchange).with("ttlQueueA");
    }

    //声明ttl队列 ttlQueueB ttl 为 40s 并绑定到对应的死信交换机以及对应的路由键，再有死信交换机去路由到匹配的队列上
    @Bean("ttlQueueB")
    public Queue queueB() {
        Map<String, Object> args = new HashMap<>(3);
        //声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", "deadExchange");
        //声明当前队列的死信路由 key，进而由死信交换机分发
        args.put("x-dead-letter-routing-key", "deadQueueB");
        //声明队列的 TTL
        args.put("x-message-ttl", 40000);
        //return new Queue("ttlQueueB",true,false,false,args);
        return QueueBuilder.durable("ttlQueueB").withArguments(args).build();
    }

    //声明队列 ttlQueueB 绑定 ttlExchange 交换机
    @Bean
    public Binding queuebBindingttlB(@Qualifier("ttlQueueB") Queue ttlQueueB,
                                  @Qualifier("ttlExchange") DirectExchange ttlExchange) {
        return BindingBuilder.bind(ttlQueueB).to(ttlExchange).with("ttlQueueB");
    }

    //声明死信队列 deadQueueB
    @Bean("deadQueueB")
    public Queue deadQueueB() {
        return new Queue("deadQueueB");
    }
    //声明死信队列 deadQueueA
    @Bean("deadQueueA")
    public Queue deadQueueA() {
        return new Queue("deadQueueA");
    }
    //声明死信队列 deadQueueA
    @Bean("deadQueueC")
    public Queue deadQueueC() {
        return new Queue("deadQueueC");
    }

    //声明死信队列 B 绑定关系
    @Bean
    public Binding deadLetterBindingB(@Qualifier("deadQueueB") Queue deadQueueB,
                                        @Qualifier("deadExchange") DirectExchange deadExchange) {
        return BindingBuilder.bind(deadQueueB).to(deadExchange).with("deadQueueB");
    }
    //声明死信队列 A 绑定关系
    @Bean
    public Binding deadLetterBindingA(@Qualifier("deadQueueA") Queue deadQueueA,
                                        @Qualifier("deadExchange") DirectExchange deadExchange) {
        return BindingBuilder.bind(deadQueueA).to(deadExchange).with("deadQueuea");
    }
    //声明死信队列 A 绑定关系
    @Bean
    public Binding deadLetterBindingC(@Qualifier("deadQueueC") Queue deadQueueC,
                                       @Qualifier("deadExchange") DirectExchange deadExchange) {
        return BindingBuilder.bind(deadQueueC).to(deadExchange).with("deadQueueC");
    }
}
