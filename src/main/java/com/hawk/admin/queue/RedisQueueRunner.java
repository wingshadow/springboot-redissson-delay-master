package com.hawk.admin.queue;

import com.hawk.admin.delay.OrderPaymentTimeout;
import com.hawk.admin.delay.RedisDelayQueueEnum;
import com.hawk.admin.delay.RedisDelayQueueUtil;
import jodd.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program: springboot-redissson-delay-master
 * @description:
 * @author: zhb
 * @create: 2023-03-24 10:15
 */

@Slf4j
@Component
public class RedisQueueRunner implements CommandLineRunner {
    @Autowired
    private RedisQueueUtil redisQueueUtil;
    @Autowired
    private OrderPaymentTimeout orderPaymentTimeout;

    ThreadPoolExecutor executorService = new ThreadPoolExecutor(3, 5, 30, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(1000),
            new ThreadFactoryBuilder().setNameFormat("order-queue-%d").get());

    @Async
    @Override
    public void run(String... args) throws Exception {
        while (true) {
            try {
                Object value = redisQueueUtil.getQueue("SAMPLE");
                if (value != null) {
                    executorService.execute(() -> {
                        orderPaymentTimeout.execute((Map) value);
                    });
                }
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                log.error("(Redisson延迟队列监测异常中断) {}", e.getMessage());
            }
        }
    }
}