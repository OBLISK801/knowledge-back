package com.lei.enums;

/**
 * @Author LEI
 * @Date 2021/2/16 22:47
 * @Description 用户类型
 */
public enum UserTypeEnum {
    SYSTEM_ADMIN(0),//系统管理员admin

    SYSTEM_USER(1);//系统的普通用户

    private int typeCode;

    UserTypeEnum(int typeCode) {
        this.typeCode = typeCode;
    }

    public int getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(int typeCode) {
        this.typeCode = typeCode;
    }
}
