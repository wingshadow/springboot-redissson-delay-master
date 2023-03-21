package com.hawk.mybatis.common.web;

import lombok.Data;

/**
 * @program: springboot3-mybatis
 * @description:
 * @author: zhb
 * @create: 2023-02-14 15:08
 */
@Data
public class RespMsg<T> {
    private int code;

    private String msg;

    private T data;

    public RespMsg() {
    }

    public RespMsg(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public RespMsg(int code) {
        this.code = code;
    }

    public RespMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public RespMsg(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public RespMsg(RespCode respCode) {
        this.code = respCode.getValue();
        this.msg = respCode.getMsg();
    }

    public RespMsg(RespCode respCode, T data) {
        this.code = respCode.getValue();
        this.msg = respCode.getMsg();
        this.data = data;
    }

    public static <T> RespMsg<T> ok() {
        return new RespMsg<T>(RespCode.SUCCESS);
    }

    public static <T> RespMsg<T> ok(T data) {
        return new RespMsg<T>(RespCode.SUCCESS, data);
    }

    public static <T> RespMsg<T> ok(String msg, T data) {
        return new RespMsg<T>(RespCode.SUCCESS.getValue(), msg, data);
    }

    public static <T> RespMsg<T> fail() {
        return new RespMsg<T>(RespCode.FAIL);
    }

    public static <T> RespMsg<T> fail(String msg) {
        return new RespMsg<T>(RespCode.FAIL.getValue(), msg,null);
    }

    public static <T> RespMsg<T> fail(RespCode respCode) {
        return new RespMsg<T>(respCode);
    }

    public static <T> RespMsg<T> fail(int code) {
        return new RespMsg<T>(code);
    }

    public static <T> RespMsg<T> fail(int code, String msg) {
        return new RespMsg<T>(code, msg);
    }
}