package com.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author ming.li
 * @date 2023/2/28 22:13
 */
@RestController
@RequestMapping("/producer")
public class ProducerController {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @RequestMapping("/helloWorld")
    public String HelloWorld(){
        String messageId = String.valueOf(UUID.randomUUID());

        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> map=new HashMap<>();
        map.put("messageId",messageId);

        map.put("createTime",createTime);
        for (int i = 0; i < 10; i++) {
            String messageData = "test message, HelloWorld!:"+i;
            map.put("messageData",messageData);
            //交换机 队列 消息
            rabbitTemplate.convertAndSend(null,"helloWorld",map);
        }
        return "ok";
    }

    @RequestMapping("/workqueues")
    public String Workqueues(){
        String messageId = String.valueOf(UUID.randomUUID());

        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> map=new HashMap<>();
        map.put("messageId",messageId);

        map.put("createTime",createTime);
        for (int i = 0; i < 10; i++) {
            String messageData = "test message, HelloWorld!:"+i;
            map.put("messageData",messageData);
            //交换机 队列(routingKey) 消息
            rabbitTemplate.convertAndSend(null,"workqueues",map);
        }
        return "ok";
    }

    @RequestMapping("/fanout")
    public String Fanout(){
        String messageId = String.valueOf(UUID.randomUUID());

        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> map=new HashMap<>();
        map.put("messageId",messageId);

        map.put("createTime",createTime);
        for (int i = 0; i < 10; i++) {
            String messageData = "test message, Fanout!:"+i;
            map.put("messageData",messageData);
            //交换机 队列(routingKey) 消息
            rabbitTemplate.convertAndSend("fanoutExchange","",map);
        }
        return "ok";
    }

    @RequestMapping("/driect")
    public String Direct(@RequestBody Map<String,String> hashMap){
        System.out.println(hashMap.get("str"));
        String messageId = String.valueOf(UUID.randomUUID());

        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> map=new HashMap<>();
        map.put("messageId",messageId);

        map.put("createTime",createTime);
        for (int i = 0; i < 10; i++) {
            String messageData = "test message, driect!:"+i;
            map.put("messageData",messageData);
            //交换机 队列(routingKey) 消息
            rabbitTemplate.convertAndSend("directExchange",hashMap.get("str"),map);
        }
        return "ok";
    }

    @RequestMapping("/topic")
    public String Topic(@RequestBody Map<String,String> hashMap){
        System.out.println(hashMap.get("str"));
        String messageId = String.valueOf(UUID.randomUUID());

        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> map=new HashMap<>();
        map.put("messageId",messageId);

        map.put("createTime",createTime);
        for (int i = 0; i < 10; i++) {
            String messageData = "test message, topic!:"+i;
            map.put("messageData",messageData);
            //交换机 队列(routingKey) 消息
            rabbitTemplate.convertAndSend("topicExchange",hashMap.get("str"),map);
        }
        return "ok";
    }
    @RequestMapping("/fanoutTest")
    public String fanoutTest(){
        String messageId = String.valueOf(UUID.randomUUID());

        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> map=new HashMap<>();
        map.put("messageId",messageId);

        map.put("createTime",createTime);
        for (int i = 0; i < 10; i++) {
            String messageData = "test message, topic!:"+i;
            map.put("messageData",messageData);
            //交换机 队列(routingKey) 消息
            rabbitTemplate.convertAndSend("liming_test","",map);
        }
        return "ok";
    }

    @RequestMapping("/fanoutExchangeTest2")
    public String fanoutExchangeTest2(){
        String messageId = String.valueOf(UUID.randomUUID());

        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> map=new HashMap<>();
        map.put("messageId",messageId);

        map.put("createTime",createTime);
        for (int i = 0; i < 10; i++) {
            String messageData = "test message, fanout_exchange_test2!:"+i;
            map.put("messageData",messageData);
            //交换机 队列(routingKey) 消息
            rabbitTemplate.convertAndSend("fanout_exchange_test2","",map);
        }
        return "ok";
    }
}
