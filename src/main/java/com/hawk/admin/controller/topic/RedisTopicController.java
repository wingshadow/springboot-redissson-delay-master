package com.hawk.admin.controller.topic;

import com.hawk.admin.stream.RedisTopicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: springboot-redissson-delay-master
 * @description:
 * @author: zhb
 * @create: 2023-03-25 12:19
 */

@RestController
@RequestMapping(value = "/topic")
public class RedisTopicController {
    @Autowired
    private RedisTopicUtil redisTopicUtil;

    @PostMapping(value = "/add")
    public ResponseEntity addQueue() {
        for (int i = 100; i < 200; i++) {
            redisTopicUtil.add("topic-" + i + "0", "EMAIL");
        }
        return ResponseEntity.ok("suc");
    }
}
