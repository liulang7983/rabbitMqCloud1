package com.controller;

import com.config.MessageCorrelationData;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author ming.li
 * @date 2023/2/28 22:13
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @RequestMapping("/helloWorld")
    public String HelloWorld(){
        String messageId = String.valueOf(UUID.randomUUID());
        for (int i = 0; i < 10; i++) {
            long l = System.currentTimeMillis();
            String str="时间戳为:"+l+",发送序号为:"+i;
            Map<String,Object> map=new HashMap<>();
            map.put("messageId",messageId);
            map.put("createTime",l);
            String messageData = "test message, HelloWorld!:"+i;
            map.put("messageData",messageData);
            //顺序发送，但是接收却不是顺序
            /*RabbitTemplate.convertAndSend 是 “伪同步”
            convertAndSend 方法在默认配置下是同步返回（即调用后立即返回），
            但底层通过 Channel 发送消息的过程是异步 IO 操作（网络传输是非阻塞的）。
            这意味着：即使你在单线程中按 i=0→1→2→...→9 的顺序调用发送方法，
            消息实际通过网络到达 RabbitMQ 服务器的顺序可能因传输延迟差异而乱序（例如 i=2 的消息可能先于 i=1 到达服务器）*/

            // 创建CorrelationData，用seq作为唯一标识
            CorrelationData correlationData = new CorrelationData(str);
            //交换机 队列/或者routingKey 消息
            rabbitTemplate.convertAndSend(null,"helloWorld",map,correlationData);
            System.out.println("发送的时间是:"+System.currentTimeMillis()+"发送的序号是:"+i);
        }
        return "ok";
    }
    @RequestMapping("/HelloWorldOrder")
    public String HelloWorldOrder()throws Exception{
        String messageId = String.valueOf(UUID.randomUUID());
        for (int i = 0; i < 10; i++) {
            long l = System.currentTimeMillis();
            String str="时间戳为:"+l+",发送序号为:"+i;
            Map<String,Object> map=new HashMap<>();
            map.put("messageId",messageId);
            map.put("createTime",l);
            String messageData = "test message, HelloWorld!:"+i;
            map.put("messageData",messageData);
            //顺序发送，但是接收却不是顺序，加上毫秒看看把时间作为参数看看是否顺序发送的
            //通过加上毫秒，可以得出是顺序发送，但是由于回调机制，导致确实时间不一致之类的，进而导致到队列时间不一致
            //出现了createTime参数更小的反而后消费(消费者的打印的索引deliveryTag 是顺序的，只是我们消息不是顺序了
            // 意思就是进入队列的顺序是乱了)
            // 用于等待确认的计数器
            CountDownLatch latch = new CountDownLatch(1);
            // 创建CorrelationData，用seq作为唯一标识
            MessageCorrelationData correlationData = new MessageCorrelationData(messageId,latch);
            correlationData.setFailed(true);
            //交换机 队列/或者routingKey 消息
            rabbitTemplate.convertAndSend(null,"helloWorld",map,correlationData);
            // 等待消息确认，最多等待10秒
            boolean confirmed = latch.await(10, TimeUnit.SECONDS);
            if (!confirmed) {
                System.err.println("消息确认超时: " + messageId);
                // 可以根据业务需求决定是否重试或跳过
            }
            System.out.println(correlationData.isFailed());
        }
        return "ok";
    }
}
