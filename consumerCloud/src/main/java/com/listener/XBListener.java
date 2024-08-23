package com.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author ming.li
 * @date 2023/2/28 22:32
 */
@Component
@RabbitListener(queues = "XB")
public class XBListener {
    //process接收的message需要前后格式一致，生产者是String则消费者也是
    @RabbitHandler
    public void process(String testMessage, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        System.out.println("XBListener消费者收到消息  : " + testMessage);
        channel.basicAck(deliveryTag,true);
    }

}
