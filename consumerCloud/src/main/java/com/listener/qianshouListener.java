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
import java.util.concurrent.TimeoutException;

/**
 * @author ming.li
 * @date 2023/3/21 13:42
 */
@Component
public class qianshouListener {
    @RabbitListener(queues = "qianshouQueue1")
    @RabbitHandler
    public void receive1(Message message, Channel channel) {
        System.out.println("qianshouQueue1消费者收到消息  : " + message);
        try {
            Thread.sleep(2000);
            long deliveryTag = message.getMessageProperties().getDeliveryTag();
            channel.basicAck(deliveryTag,false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "qianshouQueue2")
    @RabbitHandler
    public void receive2(Map message, Channel channel,@Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        System.out.println("qianshouQueue2消费者收到消息  : " + message);
        //long deliveryTag = message.getMessageProperties().getDeliveryTag();
        System.out.println("deliveryTag:"+deliveryTag);
        try {
            Thread.sleep(1000);
            System.out.println(message);
            Long messageData=Long.valueOf(message.get("messageData").toString()) ;
            System.out.println("messageData:"+messageData);
            /*if (messageData==9L){
                Long b=deliveryTag/0;
            }*/
            channel.basicAck(deliveryTag,true);

        } catch (Exception e) {
            e.printStackTrace();
            try {
                Thread.sleep(2000);
                /**
                 * 有异常就绝收消息
                 * basicNack(long deliveryTag, boolean multiple, boolean requeue)
                 * requeue:true为将消息重返当前消息队列,还可以重新发送给消费者;
                 *         false:将消息丢弃
                 */
                // long deliveryTag, boolean multiple, boolean requeue
                channel.basicNack(deliveryTag,false,true);
                //放回消息队列 第二个参数，true会重新放回队列，所以需要自己根据业务逻辑判断什么时候使用拒绝
                //此时会导致死循环一直丢回队列一直重新消费，一般是可以加入在后面数据库啥的，设置重试次数count，
                //三次后把参数写入到数据库,然后签收，后续人为分析优化代码或者人为处理
                channel.basicReject(deliveryTag,true);
            } catch (Exception ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
