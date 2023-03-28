package com.hawk.admin.delay;

/**
 * @program: springboot3-mybatis
 * @description:
 * @author: zhb
 * @create: 2023-03-20 10:21
 */

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.Record;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 订单支付超时处理类
 */
@Component
@Slf4j
public class OrderPaymentTimeout implements RedisDelayQueueHandle<Map> {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void execute(Map map) {
        try{
//             创建消息记录, 以及指定stream
            ObjectRecord<String, String> stringRecord =
                    Record.of(JSONObject.toJSONString(map)).withStreamKey("mystream");
            // 将消息添加至消息队列中
            this.stringRedisTemplate.opsForStream().add(stringRecord);
//            log.info("{}", JSON.toJSONString(map));
        }catch (Exception e){
            log.info("pubClassRecord error:{}", ExceptionUtil.stacktraceToString(e));
        }
    }
}