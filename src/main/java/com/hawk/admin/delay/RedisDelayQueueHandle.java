package com.hawk.admin.delay;

/**
 * @program: springboot3-mybatis
 * @description:
 * @author: zhb
 * @create: 2023-03-20 10:21
 */
/**
 * 延迟队列执行器
 */
public interface RedisDelayQueueHandle<T> {

    void execute(T t);

}