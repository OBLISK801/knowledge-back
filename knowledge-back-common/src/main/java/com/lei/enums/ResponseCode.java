package com.lei.enums;

/**
 * @Author LEI
 * @Date 2021/1/19 15:16
 * @Description 统一返回状态码
 */
public enum ResponseCode {
    SUCCESS(20000, "请求成功"),
    ERROR(20001, "请求失败");

    private Integer code;
    private String message;

    ResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }
}
