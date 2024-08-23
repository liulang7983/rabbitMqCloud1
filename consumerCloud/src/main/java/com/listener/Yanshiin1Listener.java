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
@RabbitListener(queues = "yanshiin1")
public class Yanshiin1Listener {
    //设置x-message-ttl为10秒，此时设置了spring.rabbitmq.listener.simple.prefetch=1每次拉取一个消息，
    // 此时每次不签收丢弃，丢弃的会跑到死信队列中
    @RabbitHandler
    public void process(Map testMessage, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        System.out.println("yanshiin1Listener消费者收到消息  : " + testMessage.toString());
        //channel.basicQos(1);
        // long deliveryTag, boolean multiple, boolean requeue
        channel.basicNack(deliveryTag,false,true);
        //放回消息队列 第二个参数，true会重新放回队列，所以需要自己根据业务逻辑判断什么时候使用拒绝
        //此时会导致死循环一直丢回队列一直重新消费，一般是可以加入在后面数据库啥的，设置重试次数count，
        //三次后把参数写入到数据库,然后签收，后续人为分析优化代码或者人为处理
        channel.basicReject(deliveryTag,true);
    }

}
