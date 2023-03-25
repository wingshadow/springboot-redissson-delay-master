package com.hawk.admin.stream;

import jodd.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program: springboot-redissson-delay-master
 * @description:
 * @author: zhb
 * @create: 2023-03-25 12:06
 */
@Slf4j
@Component
public class RedisTopicRunner implements CommandLineRunner {

    @Autowired
    private RedissonClient redissonClient;
    ThreadPoolExecutor executorService = new ThreadPoolExecutor(3, 5, 30, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(1000),
            new ThreadFactoryBuilder().setNameFormat("order-topic-%d").get());

    @Override
    public void run(String... args) throws Exception {
        new Thread(() -> {
            RTopic rTopic = redissonClient.getTopic("EMAIL");
            executorService.execute(() -> {
                rTopic.addListener(String.class, new MessageListener<String>() {
                    @Override
                    public void onMessage(CharSequence charSequence, String s) {
                        log.info("msg:{}", s);
                    }
                });
            });

        }).start();
    }
}
