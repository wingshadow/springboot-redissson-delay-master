package com.hawk.admin.delay;

/**
 * @program: springboot3-mybatis
 * @description:
 * @author: zhb
 * @create: 2023-03-20 10:22
 */

import jodd.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * description: 启动延迟队列监测扫描
 *
 * @author: shf
 * date: 2021/8/27 14:16
 */
@Slf4j
@Component
public class RedisDelayQueueRunner implements CommandLineRunner {
    @Autowired
    private RedisDelayQueueUtil redisDelayQueueUtil;
    @Autowired
    private ApplicationContext context;
    @Autowired
    private ThreadPoolTaskExecutor ptask;
    @Autowired
    private OrderPaymentTimeout orderPaymentTimeout;

    ThreadPoolExecutor executorService = new ThreadPoolExecutor(3, 5, 30, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(1000),
            new ThreadFactoryBuilder().setNameFormat("order-delay-%d").get());

    @Async
    @Override
    public void run(String... args) throws Exception {
            while (true) {
                try {
                        Object value = redisDelayQueueUtil.getDelayQueue(RedisDelayQueueEnum.ORDER_PAYMENT_TIMEOUT.getCode());
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