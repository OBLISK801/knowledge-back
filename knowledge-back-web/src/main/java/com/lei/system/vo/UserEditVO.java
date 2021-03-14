package com.lei.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author LEI
 * @Date 2021/2/22 10:32
 * @Description TODO
 */
@Data
public class UserEditVO {
    private Long id;

    private String username;

    private String nickname;

    private String email;

    private String phoneNumber;

    private Integer sex;

    private Date birth;

    private Long departmentId;
}
