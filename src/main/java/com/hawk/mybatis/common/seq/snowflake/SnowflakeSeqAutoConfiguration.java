package com.hawk.mybatis.common.seq.snowflake;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 分布式ID生成器
 * 
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "flyshadow.seq", name = "snowflake", matchIfMissing = true)
public class SnowflakeSeqAutoConfiguration {
    

    /**
     * @return 分布式ID生成器参数配置
     */
    @Bean
    @ConfigurationProperties(prefix = "flyshadow.seq.snowflake")
    public SnowflakeProp snowflakeProp() {
        return new SnowflakeProp();
    }
    
    @Bean
    @ConditionalOnMissingBean(name = "snowflakeSeq")
    public SnowflakeSeq snowflakeSeq(@Qualifier("snowflakeProp") SnowflakeProp snowflakeProp) {
        log.info("snowflakeProp : {}", snowflakeProp);
        return new SnowflakeSeq(snowflakeProp);
    }

    @Bean
    public Snowflake snowflake() {
        return new Snowflake();
    }
}
