package com.controller;

import com.config.TtlSiXinConfig;
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
 * @date 2023/3/1 16:28
 */
@RestController
@RequestMapping("/yanshi")
public class YanshiController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @RequestMapping("/yanshi")
    public String ttl() {
        String messageId = String.valueOf(UUID.randomUUID());
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);

        map.put("createTime", createTime);
        for (int i = 0; i < 10; i++) {
            String messageData = "test message, yanshi!:" + i;
            map.put("messageData", messageData);
            //交换机 队列(routingKey) 消息
            map.put("yyy","延时队列测试");
            rabbitTemplate.convertAndSend("exchangeYanshiin", "", map);
        }
        return "ok";
    }
}