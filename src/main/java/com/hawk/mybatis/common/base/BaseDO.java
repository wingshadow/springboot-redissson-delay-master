package com.hawk.mybatis.common.base;

import lombok.Data;

import javax.persistence.Id;

/**
 * @program: springboot3-mybatis
 * @description:
 * @author: zhb
 * @create: 2023-02-14 11:03
 */
@Data
public class BaseDO {
    @Id
    private Long id;
}