package com.hawk.admin.orm.service.impl;

import com.github.pagehelper.PageInfo;
import com.hawk.admin.orm.dao.UserMapper;
import com.hawk.admin.orm.entity.User;
import com.hawk.admin.orm.service.UserService;
import com.hawk.mybatis.common.database.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: springboot3-mybatis
 * @description:
 * @author: zhb
 * @create: 2023-02-14 15:01
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public PageInfo<User> listByPage(User paramBean, final int pageNum, final int pageSize) {
        PageInfo<User> pageInfo = super.listByPage(paramBean, pageNum, pageSize);
        List<User> list = pageInfo.getList();
        pageInfo.setList(list);
        return pageInfo;
    }

    @Override
    public List<User> listByName(String name) {
        return userMapper.selectByName(name);
    }
}