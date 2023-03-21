package com.hawk.admin.delay;

/**
 * @program: springboot3-mybatis
 * @description:
 * @author: zhb
 * @create: 2023-03-20 10:21
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 订单支付超时处理类
 */
@Component
@Slf4j
public class OrderPaymentTimeout implements RedisDelayQueueHandle<Map> {
    @Override
    public void execute(Map map) {
        log.info("{}", map);
        // TODO 订单支付超时，自动取消订单处理业务...

    }
}