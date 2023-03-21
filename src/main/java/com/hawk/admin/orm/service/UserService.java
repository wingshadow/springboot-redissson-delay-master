package com.hawk.admin.orm.service;

import com.hawk.admin.orm.entity.User;
import com.hawk.mybatis.common.database.BaseService;

import java.util.List;

/**
 * @program: springboot3-mybatis
 * @description:
 * @author: zhb
 * @create: 2023-02-14 14:58
 */
public interface UserService extends BaseService<User> {
    List<User> listByName(String name);
}