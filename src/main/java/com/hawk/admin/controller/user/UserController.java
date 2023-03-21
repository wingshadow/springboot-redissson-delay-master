package com.hawk.admin.controller.user;

import com.github.pagehelper.PageInfo;
import com.hawk.admin.orm.entity.User;
import com.hawk.admin.orm.service.UserService;
import com.hawk.mybatis.common.web.RespMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: springboot3-mybatis
 * @description:
 * @author: zhb
 * @create: 2023-02-14 15:06
 */
@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "list")
    public RespMsg list() {
        // RespMsg不添加getter和setter方法导致无法解析json
        PageInfo<User> pageInfo = userService.listByPage(null, 1, 10);
        return RespMsg.ok(pageInfo);
    }
}