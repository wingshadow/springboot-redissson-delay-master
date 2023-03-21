package com.hawk.admin.delay;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @program: springboot3-mybatis
 * @description:
 * @author: zhb
 * @create: 2023-03-20 10:20
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum RedisDelayQueueEnum {

    ORDER_PAYMENT_TIMEOUT("ORDER_PAYMENT_TIMEOUT","订单支付超时，自动取消订单", "orderPaymentTimeout");

    /**
     * 延迟队列 Redis Key
     */
    private String code;

    /**
     * 中文描述
     */
    private String name;

    /**
     * 延迟队列具体业务实现的 Bean
     * 可通过 Spring 的上下文获取
     */
    private String beanId;

}