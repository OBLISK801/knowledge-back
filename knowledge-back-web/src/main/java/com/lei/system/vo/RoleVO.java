package com.lei.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author LEI
 * @Date 2021/2/20 20:00
 * @Description TODO
 */
@Data
public class RoleVO {
    private Long id;

    private String roleName;

    private String remark;

    private Date createTime;

    private Date modifiedTime;

    private Boolean status;
}
