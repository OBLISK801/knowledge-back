package com.lei.system.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Author LEI
 * @Date 2021/2/21 14:52
 * @Description TODO
 */
@Data
public class UserVO {

    private Long id;

    private String username;

    private String nickname;

    private String email;

    private String phoneNumber;

    private Boolean status;

    private Date createTime;

    private Integer sex;

    private Date birth;

    private String password;

}
