package com.hawk.admin.queue;

import cn.hutool.core.util.StrUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @program: springboot-redissson-delay-master
 * @description:
 * @author: zhb
 * @create: 2023-03-24 10:05
 */
@Slf4j
@Component
@ConditionalOnBean({RedissonClient.class})
public class RedisQueueUtil {
    @Autowired
    private RedissonClient redissonClient;

    public <T> boolean addQueue(@NonNull T value, @NonNull String queueCode) {
        if (StrUtil.isBlank(queueCode) || Objects.isNull(value)) {
            return false;
        }
        try {
            RQueue queue = redissonClient.getQueue(queueCode);
            queue.add(value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public <T> T getQueue(@NonNull String queueCode) throws InterruptedException {
        if (StrUtil.isBlank(queueCode)) {
            return null;
        }
        RQueue queue = redissonClient.getQueue(queueCode);
        queue.peek();
        T value = (T) queue.poll();
        return value;
    }
}