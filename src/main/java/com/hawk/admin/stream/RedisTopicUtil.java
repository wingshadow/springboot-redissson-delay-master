package com.hawk.admin.stream;

import cn.hutool.core.util.StrUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RQueue;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

/**
 * @program: springboot-redissson-delay-master
 * @description:
 * @author: zhb
 * @create: 2023-03-25 11:52
 */
@Slf4j
@Component
@ConditionalOnBean({RedissonClient.class})
public class RedisTopicUtil {
    @Autowired
    private RedissonClient redissonClient;

    public <T> boolean add(@NonNull T value, @NonNull String topic) {
        if(StrUtil.isBlank(topic)){
            return false;
        }
        RTopic rTopic = redissonClient.getTopic(topic);
        rTopic.publish(value);
        return true;
    }

}
