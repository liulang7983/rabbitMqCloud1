package com.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author ming.li
 * @date 2023/3/1 15:23
 */
@RestController
@RequestMapping("/confirmOrRetrun")
public class ConfirmOrRetrunController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //消息推送到server，但是在server里找不到交换机
    @RequestMapping("/noExchange")
    public String noExchange(){
        String messageId = String.valueOf(UUID.randomUUID());
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> map=new HashMap<>();
        map.put("createTime",createTime);
        map.put("messageId",messageId);
        String messageData = "test message, HelloWorld!:";
        map.put("messageData",messageData);
        //交换机 队列 消息
        rabbitTemplate.convertAndSend("ssdfg",null,map);
        return "ok";
    }

    //消息推送到server，找到交换机了，但是没找到队列
    @RequestMapping("/noQueue")
    public String noQueue(){
        String messageId = String.valueOf(UUID.randomUUID());
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> map=new HashMap<>();
        map.put("createTime",createTime);
        map.put("messageId",messageId);
        String messageData = "test message, HelloWorld!:";
        map.put("messageData",messageData);
        //交换机 队列 消息
        rabbitTemplate.convertAndSend("topicExchange","lllll",map);
        return "ok";
    }

    //消息推送到server，找到交换机了，同时找到队列
    @RequestMapping("/inQueue")
    public String inQueue(){
        String messageId = String.valueOf(UUID.randomUUID());
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> map=new HashMap<>();
        map.put("createTime",createTime);
        map.put("messageId",messageId);
        String messageData = "test message, HelloWorld!:";
        map.put("messageData",messageData);
        //交换机 队列 消息
        rabbitTemplate.convertAndSend("topicExchange","topic",map);
        return "ok";
    }
}
