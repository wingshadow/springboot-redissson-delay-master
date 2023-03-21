package com.hawk;


import com.alibaba.fastjson.JSON;
import com.hawk.admin.orm.entity.User;
import com.hawk.admin.orm.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Main.class})
public class MainTest {

    @Resource
    private UserService userService;

    @Test
    public void test(){
        List<User> list = userService.listAll();
        System.out.println(JSON.toJSONString(list));
    }

    @Test
    public void test1(){
        List<User> list = userService.listByName(null);
        System.out.println(JSON.toJSONString(list));
    }
}