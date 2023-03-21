package com.hawk.mybatis.common.seq.snowflake;

/**
 * 基于Snowflake算法的全局ID生成器异常类
 * 
 */
public class SnowflakeException extends RuntimeException {
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4853521468883834204L;
    
    public SnowflakeException(String message) {
        super(message);
    }
    
}
