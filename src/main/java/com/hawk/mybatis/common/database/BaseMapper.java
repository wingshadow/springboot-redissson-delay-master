package com.hawk.mybatis.common.database;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @program: springboot3-mybatis
 * @description:
 * @author: zhb
 * @create: 2023-02-14 14:05
 */
public interface BaseMapper <T> extends Mapper<T>, MySqlMapper<T> {
}
