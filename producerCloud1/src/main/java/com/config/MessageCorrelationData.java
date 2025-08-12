package com.config;

/**
 * @Author ming.li
 * @Date 2025/8/12 14:23
 * @Version 1.0
 */
import org.springframework.amqp.rabbit.connection.CorrelationData;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;

public class MessageCorrelationData extends CorrelationData {
    // 用于等待确认的计数器
    private CountDownLatch latch;
    private boolean failed;

    // 构造方法
    public MessageCorrelationData() {
        super();
    }

    public MessageCorrelationData(String id) {
        super(id);
    }

    public MessageCorrelationData(CountDownLatch latch) {
        super(UUID.randomUUID().toString());
        this.latch = latch;
    }

    public MessageCorrelationData(String id, CountDownLatch latch) {
        super(id);
        this.latch = latch;
    }

    // getter和setter
    public CountDownLatch getLatch() {
        return latch;
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    public boolean isFailed() {
        return failed;
    }

    public void setFailed(boolean failed) {
        this.failed = failed;
    }
}
