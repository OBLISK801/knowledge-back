package com.lei.system.service;

import com.lei.error.SystemException;
import com.lei.system.entity.Menu;
import com.lei.system.entity.Role;
import com.lei.system.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lei.system.vo.*;
import com.lei.utils.PageUtils;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-02-12
 */
public interface IUserService extends IService<User> {
    User findUserByName(String username);

    List<Role> findRolesById(Long id) throws SystemException;

    List<Menu> findMenuByRoles(List<Role> roles);

    String login(String username, String password) throws SystemException;

    UserInfoVO info();

    List<MenuNodeVO> findMenu();

    void register(UserVO userVO) throws SystemException;

    PageUtils<UserVO> findUserList(Integer pageNum, Integer pageSize, UserVO userVO);

    void assignRoles(Long id, Long[] rids) throws SystemException;

    void deleteById(Long id) throws SystemException;

    void updateStatus(Long id, Boolean status) throws SystemException;

    void update(Long id, UserEditVO userVO) throws SystemException;

    UserEditVO edit(Long id) throws SystemException;

    List<Long> roles(Long id) throws SystemException;

    List<User> findAll();

    void changeAvatar(AvatarVO avatarVO);
}
