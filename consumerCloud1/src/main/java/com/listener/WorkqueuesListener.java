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
@RabbitListener(queues = "workqueues")
public class WorkqueuesListener {
    //发送三十个消息不管消费能力，均分给了两个消费者
    @RabbitHandler
    public void process(Map testMessage , Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag)throws IOException {
        System.out.println("WorkqueuesListener消费者收到消息  : " + testMessage.toString());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        channel.basicQos(1);
        System.out.println("索引:"+deliveryTag);
        //channel.basicAck(deliveryTag,true);
    }

}
