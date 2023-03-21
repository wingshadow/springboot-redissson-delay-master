package com.hawk.admin.controller.delay;

import com.hawk.admin.delay.RedisDelayQueueEnum;
import com.hawk.admin.delay.RedisDelayQueueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @program: springboot3-mybatis
 * @description:
 * @author: zhb
 * @create: 2023-03-20 10:27
 */
@RestController
@RequestMapping(value = "/delay")
public class RedisDelayQueueController {

    @Autowired
    private RedisDelayQueueUtil redisDelayQueueUtil;

    @PostMapping(value = "/add")
    public ResponseEntity delay() {


        for (int i = 0; i < 100; i++) {
            Map<String, String> map1 = new HashMap<>();
            map1.put("orderId", i + "0");
            map1.put("remark", "订单支付超时，自动取消订单");
            // 添加订单支付超时，自动取消订单延迟队列。为了测试效果，延迟10秒钟
            redisDelayQueueUtil.addDelayQueue(map1, 10, TimeUnit.SECONDS, RedisDelayQueueEnum.ORDER_PAYMENT_TIMEOUT.getCode());
        }
        return ResponseEntity.ok("suc");
    }
}