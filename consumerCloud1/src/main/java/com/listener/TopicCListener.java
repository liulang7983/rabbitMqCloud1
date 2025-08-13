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
@RabbitListener(queues = "topicC")
public class TopicCListener {
    /*//此时会只消费一条就不动了，因为改了配置的自动提交为手动提交，需要手动提交
    @RabbitHandler
    public void process(Map testMessage) {
        System.out.println("TopicCListener消费者收到消息  : " + testMessage.toString());
    }*/
    @RabbitHandler
    public void process(Map testMessage, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        System.out.println("TopicCListener消费者收到消息  : " + testMessage.toString());
        channel.basicAck(deliveryTag,false);
    }
}
