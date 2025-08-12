package com.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ming.li
 * @date 2023/3/1 15:14
 */
@Configuration
public class RabbitConfig {

    /**
     * 配置连接工厂
     * 在2.2.x版本中通过ConnectionFactory配置发布确认和返回机制
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/liming");
        connectionFactory.setUsername("liming");
        connectionFactory.setPassword("liming");

        // 启用发布确认机制 - 2.2.x版本的正确方式
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);

        // 启用返回机制 - 2.2.x版本的正确方式
        connectionFactory.setPublisherReturns(true);

        return connectionFactory;
    }

    /**
     * 配置RabbitTemplate
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

        // 设置确认回调
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if (correlationData instanceof MessageCorrelationData) {
                    MessageCorrelationData msgCorrelationData = (MessageCorrelationData) correlationData;

                    if (ack) {
                        // 消息确认成功
                        msgCorrelationData.setFailed(false);
                        msgCorrelationData.getLatch().countDown();
                        System.err.println("消息确认成功: " + cause);
                    } else {
                        // 消息确认失败
                        msgCorrelationData.setFailed(true);
                        msgCorrelationData.getLatch().countDown();
                        System.err.println("消息确认失败: " + cause);
                    }
                }
            }
        });

        // 设置返回回调
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText,
                                        String exchange, String routingKey) {
                System.err.println("消息返回: " + new String(message.getBody()) +
                        ", 回复码: " + replyCode + ", 回复文本: " + replyText);
            }
        });

        return rabbitTemplate;
    }
}
