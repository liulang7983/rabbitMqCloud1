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
 * @date 2023/3/21 13:45
 */
@RestController
@RequestMapping("/qianshou")
public class QianshouController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @RequestMapping("/qianshou1")
    public String qianshou1(){
        String messageId = String.valueOf(UUID.randomUUID());

        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> map=new HashMap<>();
        map.put("messageId",messageId);

        map.put("createTime",createTime);
        for (int i = 0; i < 10; i++) {
            String messageData = "test message, qianshou1!:"+i;
            map.put("messageData",messageData);
            //交换机 队列 消息
            rabbitTemplate.convertAndSend("qianshouExchange1","",map);
        }
        return "ok";
    }

    @RequestMapping("/qianshou2")
    public String qianshou2(){
        String messageId = String.valueOf(UUID.randomUUID());

        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> map=new HashMap<>();
        map.put("messageId",messageId);

        map.put("createTime",createTime);
        for (int i = 0; i < 10; i++) {
            String messageData = ""+i;
            map.put("messageData",messageData);
            //交换机 队列 消息
            rabbitTemplate.convertAndSend("qianshouExchange2","",map);
        }
        return "ok";
    }
}
