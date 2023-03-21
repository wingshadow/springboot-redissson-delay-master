package com.hawk.admin.orm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hawk.mybatis.common.base.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

/**
 * @program: springboot3-mybatis
 * @description:
 * @author: zhb
 * @create: 2023-02-14 14:52
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_user")
public class User extends BaseDO {

    /**
     *
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long deptId;
    /**
     * 姓名
     */
    private String name;
    /**
     * 1:身份证2:护照3:军官证4:其他
     */
    private Integer cardType;
    /**
     * 证件号码
     */
    private String idCard;
    /**
     * 1：男 2：女
     */
    private Integer gender;
    /**
     * 用户照片url
     */
    private String picture;
    /**
     * 1:学员 2：运营
     */
    private Integer type;
}