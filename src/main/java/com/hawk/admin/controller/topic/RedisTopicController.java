package com.hawk.admin.controller.topic;

import com.hawk.admin.queue.RedisQueueUtil;
import com.hawk.admin.stream.RedisTopicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: springboot-redissson-delay-master
 * @description:
 * @author: zhb
 * @create: 2023-03-25 12:19
 */

@RestController
@RequestMapping(value = "/queue")
public class RedisTopicController {
    @Autowired
    private RedisTopicUtil redisTopicUtil;

    @PostMapping(value = "/addQueue")
    public ResponseEntity addQueue() {
        for (int i = 100; i < 200; i++) {
            redisTopicUtil.add(i + "0", "EMAIL");
        }
        return ResponseEntity.ok("suc");
    }
}
