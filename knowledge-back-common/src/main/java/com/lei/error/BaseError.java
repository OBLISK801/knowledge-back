package com.lei.error;

/**
 * @Author LEI
 * @Date 2021/2/16 21:07
 * @Description 自定义的异常枚举类需要实现该接口
 */
public interface BaseError {

    int getErrorCode();

    String getErrorMsg();

    BaseError setErrorMsg(String message);
}
