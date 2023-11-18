package com.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
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
@RabbitListener(queues = "QD")
public class TtlSiXinListener {
    @RabbitHandler
    public void process(String message, Channel channel,@Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        System.out.println("TtlSiXinListener消费者收到消息  : " + message);
        channel.basicAck(deliveryTag,true);
    }

}
