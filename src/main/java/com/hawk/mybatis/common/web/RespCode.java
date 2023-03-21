package com.hawk.mybatis.common.web;

/**
 * @program: springboot3-mybatis
 * @description:
 * @author: zhb
 * @create: 2023-02-14 15:08
 */
public enum RespCode {

    SUCCESS(0, "成功"),
    FAIL(1, "执行失败");
    /**
     * 状态码
     */
    private Integer value;
    /**
     * 状态描述
     */
    private String msg;

    RespCode(int value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer code) {
        this.value = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}