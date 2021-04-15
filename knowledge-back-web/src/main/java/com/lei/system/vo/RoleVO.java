package com.lei.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dds", timezone = "GMT+8")
    private Date createTime;

    private Date modifiedTime;

    private Boolean status;
}
