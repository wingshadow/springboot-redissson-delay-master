package com.hawk.admin.controller.queue;

import com.hawk.admin.delay.RedisDelayQueueEnum;
import com.hawk.admin.delay.RedisDelayQueueUtil;
import com.hawk.admin.queue.RedisQueueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @program: springboot-redissson-delay-master
 * @description:
 * @author: zhb
 * @create: 2023-03-24 10:17
 */

@RestController
@RequestMapping(value = "/queue")
public class RedisQueueController {
    @Autowired
    private RedisQueueUtil redisQueueUtil;

    @PostMapping(value = "/add")
    public ResponseEntity delay() {


        for (int i = 0; i < 100; i++) {
            Map<String, String> map1 = new HashMap<>();
            map1.put("orderId", i + "0");
            map1.put("remark", "订单支付超时，自动取消订单");
            // 添加订单支付超时，自动取消订单延迟队列。为了测试效果，延迟10秒钟
            redisQueueUtil.addQueue(map1, "SAMPLE");
        }
        return ResponseEntity.ok("suc");
    }
}