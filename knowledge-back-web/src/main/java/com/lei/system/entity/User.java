package com.lei.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-02-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user")
@ApiModel(value="User对象", description="用户表")
@Excel("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableId(value = "id", type = IdType.AUTO)
    @ExcelField(value = "编号", width = 50)
    private Long id;

    @ExcelField(value = "用户名", width = 100)
    @ApiModelProperty(value = "用户名")
    private String username;

    @ExcelField(value = "昵称", width = 100)
    private String nickname;

    @ExcelField(value = "邮箱", width = 150)
    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ExcelField(value = "电话号码", width = 100)
    @ApiModelProperty(value = "联系电话")
    private String phoneNumber;

    @ApiModelProperty(value = "状态 0锁定 1有效")
    private Integer status;

    @ExcelField(value = "创建时间", dateFormat = "yyyy年MM月dd日 HH:mm:ss", width = 180)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ExcelField(value = "修改时间", dateFormat = "yyyy年MM月dd日 HH:mm:ss",width = 180)
    @ApiModelProperty(value = "修改时间")
    private Date modifiedTime;

    @ExcelField(//
            value = "性别",
            readConverterExp = "男=0,女=1",
            writeConverterExp = "0=男,1=女"
            ,width = 50
    )
    @ApiModelProperty(value = "性别 0男 1女 2保密")
    private Integer sex;

    @ApiModelProperty(value = "盐")
    private String salt;

    @ExcelField(//
            value = "用户类型",
            readConverterExp = "超级管理员=0,普通用户=1",
            writeConverterExp = "0=超级管理员,1=普通用户"
            ,width = 80
    )
    @ApiModelProperty(value = "0:超级管理员，1：系统用户")
    private Integer type;

    @ApiModelProperty(value = "密码")
    private String password;

    @ExcelField(value = "出生日期", dateFormat = "yyyy/MM/dd",width = 100)
    private Date birth;


}
