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
@RabbitListener(queues = "yanshiin")
public class YanshiinListener {
    //设置x-message-ttl为10秒，此时设置了spring.rabbitmq.listener.simple.prefetch=1每次拉取一个消息，
    // 此时自己会消费四个，其余的超时跑到死信队列死信队列自己消费了
    //如果没设置那么可能每次拉取的数据可能很多，此时这些消息第四个是已经超时的，但是在已拉取的消费队列里，所以不会超时去死信队列
    @RabbitHandler
    public void process(Map testMessage, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        System.out.println("yanshiinListener消费者收到消息  : " + testMessage.toString());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        channel.basicQos(1);
        channel.basicAck(deliveryTag,false);
    }

}
