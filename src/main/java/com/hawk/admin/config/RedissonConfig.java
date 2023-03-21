package com.hawk.admin.config;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: springboot3-mybatis
 * @description:
 * @author: zhb
 * @create: 2023-03-20 10:06
 */
@Slf4j
@Configuration
public class RedissonConfig {
    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private String port;

    @Value("${spring.data.redis.password}")
    private String password;

    @Value("${spring.data.redis.database}")
    private int database;
    private final String REDISSON_PREFIX = "redis://";
    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        String url = REDISSON_PREFIX + host + ":" + port;
        // 这里以单台redis服务器为例
        config.useSingleServer()
                .setAddress(url)
                .setPassword(password)
                .setDatabase(database)
                .setPingConnectionInterval(2000);
        config.setLockWatchdogTimeout(10000L);

        // 实际开发过程中应该为cluster或者哨兵模式，这里以cluster为例
        //String[] urls = {"127.0.0.1:6379", "127.0.0.2:6379"};
        //config.useClusterServers()
        //        .addNodeAddress(urls);
        try {
            return Redisson.create(config);
        } catch (Exception e) {
            log.error("RedissonClient init redis url:[{}], Exception:", url, e);
            return null;
        }
    }
}