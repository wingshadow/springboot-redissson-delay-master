package com.hawk.admin.orm.dao;

import com.hawk.admin.orm.entity.User;
import com.hawk.mybatis.common.database.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: springboot3-mybatis
 * @description:
 * @author: zhb
 * @create: 2023-02-14 14:53
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    List<User> selectByName(String name);
}