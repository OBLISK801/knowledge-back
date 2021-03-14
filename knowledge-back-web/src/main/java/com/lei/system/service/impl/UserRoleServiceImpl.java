package com.lei.system.service.impl;

import com.lei.system.entity.UserRole;
import com.lei.system.mapper.UserRoleMapper;
import com.lei.system.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色关联表 服务实现类
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-02-12
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
