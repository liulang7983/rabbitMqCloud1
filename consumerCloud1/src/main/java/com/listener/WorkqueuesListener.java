package com.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author ming.li
 * @date 2023/2/28 22:32
 */
@Component
@RabbitListener(queues = "workqueues")
public class WorkqueuesListener {
    @RabbitHandler
    public void process(Map testMessage) {
        System.out.println("WorkqueuesListener消费者收到消息  : " + testMessage.toString());
    }

}
