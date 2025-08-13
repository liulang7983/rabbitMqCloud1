package com.listener;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author ming.li
 * @date 2023/2/28 22:32
 */
@Component
@RabbitListener(queues = "helloWorldOrder")
public class HelloWorldOrderListener {
    @RabbitHandler
    public void process(byte[] testMessage, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        String message = new String(testMessage, StandardCharsets.UTF_8);
        JSONObject jsonObject = JSONObject.parseObject(message);
        System.out.println("HelloWorldOrderListener消费者收到消息  : " + message);
        System.out.println("索引:"+deliveryTag);
        //第二个参数代表是否确认比我小的索引的消息，为false是指确认自己，不盲目提交其他可能在正在处理的索引
        channel.basicAck(deliveryTag,false);
    }

}
