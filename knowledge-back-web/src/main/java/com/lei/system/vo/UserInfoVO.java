package com.lei.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @Author LEI
 * @Date 2021/2/16 22:44
 * @Description TODO
 */
@Data
public class UserInfoVO {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "菜单")
    private Set<String> url;

    @ApiModelProperty(value = "权限")
    private Set<String> perms;

    @ApiModelProperty(value = "角色集合")
    private List<String> roles;

    @ApiModelProperty(value = "是否是超管")
    private Boolean isAdmin=false;
}
