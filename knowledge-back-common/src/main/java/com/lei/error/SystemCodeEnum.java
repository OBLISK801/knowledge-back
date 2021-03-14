package com.lei.error;

/**
 * @Author LEI
 * @Date 2021/2/16 21:09
 * @Description 系统错误码与错误描述枚举
 */
public enum SystemCodeEnum implements BaseError {

    PARAMETER_ERROR(50000, "参数不合法"),
    TOKEN_ERROR(50001, "用户未认证");

    private int errorCode;

    private String errorMsg;

    SystemCodeEnum(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getErrorMsg() {
        return this.errorMsg;
    }

    @Override
    public BaseError setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }
}
