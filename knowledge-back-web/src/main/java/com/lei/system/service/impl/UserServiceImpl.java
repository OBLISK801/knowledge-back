package com.lei.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lei.enums.UserStatusEnum;
import com.lei.enums.UserTypeEnum;
import com.lei.error.SystemCodeEnum;
import com.lei.error.SystemException;
import com.lei.system.converter.MenuConverter;
import com.lei.system.converter.UserConverter;
import com.lei.system.entity.*;
import com.lei.system.jwt.JWTToken;
import com.lei.system.jwt.JWTUtils;
import com.lei.system.mapper.*;
import com.lei.system.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lei.system.utils.MenuTreeBuilder;
import com.lei.system.vo.MenuNodeVO;
import com.lei.system.vo.UserEditVO;
import com.lei.system.vo.UserInfoVO;
import com.lei.system.vo.UserVO;
import com.lei.utils.MD5Utils;
import com.lei.utils.PageUtils;
import io.swagger.annotations.Example;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-02-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private UserConverter userConverter;

    @Override
    public User findUserByName(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public List<Role> findRolesById(Long id) throws SystemException {
        User dbUser = userMapper.selectById(id);
        if (dbUser == null) {
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR, "该用户不存在");
        }
        List<Role> roles = new ArrayList<>();
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", dbUser.getId());
        List<UserRole> userRoleList = userRoleMapper.selectList(wrapper);
        List<Long> rids = new ArrayList<>();
        if (!CollectionUtils.isEmpty(userRoleList)) {
            for (UserRole userRole : userRoleList) {
                rids.add(userRole.getRoleId());
            }
            if (!CollectionUtils.isEmpty(rids)) {
                for (Long rid : rids) {
                    Role role = roleMapper.selectById(rid);
                    if (role != null) {
                        roles.add(role);
                    }
                }
            }
        }
        return roles;
    }

    @Override
    public List<Menu> findMenuByRoles(List<Role> roles) {
        List<Menu> menus = new ArrayList<>();
        if (!CollectionUtils.isEmpty(roles)) {
            // 存放用户的菜单id
            Set<Long> menuIds = new HashSet<>();
            List<RoleMenu> roleMenus;
            for (Role role : roles) {
                //根据角色ID查询权限ID
                QueryWrapper<RoleMenu> wrapper = new QueryWrapper<>();
                wrapper.eq("role_id", role.getId());
                roleMenus = roleMenuMapper.selectList(wrapper);
                if (!CollectionUtils.isEmpty(roleMenus)) {
                    for (RoleMenu roleMenu : roleMenus) {
                        menuIds.add(roleMenu.getMenuId());
                    }
                }
            }
            if (!CollectionUtils.isEmpty(menuIds)) {
                for (Long menuId : menuIds) {
                    //该用户所有的菜单
                    Menu menu = menuMapper.selectById(menuId);
                    if (menu != null) {
                        menus.add(menu);
                    }
                }
            }
        }
        System.out.println(menus);
        return menus;
    }

    @Override
    public String login(String username, String password) throws SystemException {
        String token;
        User user = findUserByName(username);
        if (user != null) {
            String salt = user.getSalt();
            //秘钥为盐
            String target = MD5Utils.md5Encryption(password, salt);
            //生成Token
            token = JWTUtils.sign(username, target);
            JWTToken jwtToken = new JWTToken(token);
            try {
                SecurityUtils.getSubject().login(jwtToken);
            } catch (AuthenticationException e) {
                throw new SystemException(SystemCodeEnum.PARAMETER_ERROR, e.getMessage());
            }
        } else {
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR, "用户不存在");
        }
        return token;
    }

    @Override
    public UserInfoVO info() {
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setAvatar(activeUser.getUser().getAvatar());
        userInfoVO.setUsername(activeUser.getUser().getUsername());
        userInfoVO.setUrl(activeUser.getUrls());
        userInfoVO.setNickname(activeUser.getUser().getNickname());
        List<String> roleNames = activeUser.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList());
        userInfoVO.setRoles(roleNames);
        userInfoVO.setPerms(activeUser.getPermissions());
        userInfoVO.setIsAdmin(activeUser.getUser().getType() == UserTypeEnum.SYSTEM_ADMIN.getTypeCode());
        return userInfoVO;
    }

    @Override
    public List<MenuNodeVO> findMenu() {
        List<Menu> menus = null;
        List<MenuNodeVO> menuNodeVOS = new ArrayList<>();
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        if (activeUser.getUser().getType() == UserTypeEnum.SYSTEM_ADMIN.getTypeCode()) {
            //超级管理员
            QueryWrapper<Menu> wrapper = new QueryWrapper<>();
            menus = menuMapper.selectList(wrapper);
        } else if (activeUser.getUser().getType() == UserTypeEnum.SYSTEM_USER.getTypeCode()) {
            //普通系统用户
            menus = activeUser.getMenus();
        }
        if (!CollectionUtils.isEmpty(menus)) {
            menuNodeVOS = MenuConverter.converterToMenuNodeVO(menus);
        }
        //构建树形菜单
        return MenuTreeBuilder.build(menuNodeVOS);
    }

    @Override
    public void register(UserVO userVO) throws SystemException {
        String username = userVO.getUsername();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        int i = userMapper.selectCount(wrapper);
        if (i != 0) {
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR, "该用户名已被占用");
        }
        User user = new User();
        BeanUtils.copyProperties(userVO, user);
        String salt = UUID.randomUUID().toString().substring(0, 32);
        user.setPassword(MD5Utils.md5Encryption(user.getPassword(), salt));
        user.setModifiedTime(new Date());
        user.setCreateTime(new Date());
        user.setSalt(salt);
        user.setType(UserTypeEnum.SYSTEM_USER.getTypeCode());//用户默认是普通用户
        user.setStatus(UserStatusEnum.AVAILABLE.getStatusCode());//添加的用户默认启用
        user.setAvatar("https://i.loli.net/2021/02/18/YGMVbsFklj5I9rd.png");
        userMapper.insert(user);
    }

    @Override
    public PageUtils<UserVO> findUserList(Integer pageNum, Integer pageSize, UserVO userVO) {
        String username = userVO.getUsername();
        String nickname = userVO.getNickname();
        Integer sex = userVO.getSex();
        String email = userVO.getEmail();
        List<User> userList = userMapper.selectListByCondition(username, nickname, sex, email);
        List<UserVO> userVOS = userConverter.converterToUserVOList(userList);
        PageUtils<UserVO> info = new PageUtils<>(pageNum, pageSize);
        info.doPage(userVOS);
        return info;
    }

    @Override
    public void assignRoles(Long id, Long[] rids) throws SystemException {
        //删除之前用户的所有角色
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR, "用户不存在");
        }
        //删除之前分配的
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", user.getId());
        userRoleMapper.delete(wrapper);
        //增加现在分配的
        if (rids.length > 0) {
            for (Long rid : rids) {
                Role role = roleMapper.selectById(rid);
                if (role == null) {
                    throw new SystemException(SystemCodeEnum.PARAMETER_ERROR, "roleId=" + rid + ",该角色不存在");
                }
                //判断角色状态
                if (role.getStatus() == 0) {
                    throw new SystemException(SystemCodeEnum.PARAMETER_ERROR, "roleName=" + role.getRoleName() + ",该角色已禁用");
                }
                UserRole userRole = new UserRole();
                userRole.setUserId(id);
                userRole.setRoleId(rid);
                userRoleMapper.insert(userRole);
            }
        }
    }

    @Override
    public void deleteById(Long id) throws SystemException {
        User user = userMapper.selectById(id);
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR, "要删除的用户不存在");
        }
        if (user.getId().equals(activeUser.getUser().getId())) {
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR, "不能删除当前登入用户");
        }
        userMapper.deleteById(id);
        //删除对应[用户-角色]记录
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", id);
        userRoleMapper.delete(wrapper);
    }

    @Override
    public void updateStatus(Long id, Boolean status) throws SystemException {
        User dbUser = userMapper.selectById(id);
        if (dbUser == null) {
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR, "要更新状态的用户不存在");
        }
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        if (dbUser.getId().equals(activeUser.getUser().getId())) {
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR, "无法改变当前用户状态");
        } else {
            User t = new User();
            t.setId(id);
            t.setStatus(status ? UserStatusEnum.DISABLE.getStatusCode() : UserStatusEnum.AVAILABLE.getStatusCode());
            UpdateWrapper<User> wrapper = new UpdateWrapper<>();
            wrapper.eq("id",id);
            userMapper.update(t,wrapper);
        }
    }

    @Override
    public void update(Long id, UserEditVO userVO) throws SystemException {
        User dbUser = userMapper.selectById(id);
        String username = userVO.getUsername();
        if(dbUser==null){
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"要删除的用户不存在");
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        List<User> users = userMapper.selectList(wrapper);
        if(!CollectionUtils.isEmpty(users)){
            if(!users.get(0).getId().equals(id)){
                throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"该用户名已被占用");
            }
        }
        User user = new User();
        BeanUtils.copyProperties(userVO,user);
        user.setModifiedTime(new Date());
        user.setId(dbUser.getId());
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",dbUser.getId());
        userMapper.update(user,updateWrapper);
    }

    @Override
    public UserEditVO edit(Long id) throws SystemException {
        User user = userMapper.selectById(id);
        if(user==null){
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"要编辑的用户不存在");
        }
        UserEditVO userEditVO = new UserEditVO();
        BeanUtils.copyProperties(user,userEditVO);
        return userEditVO;
    }

    @Override
    public List<Long> roles(Long id) throws SystemException {
        User user = userMapper.selectById(id);
        if(user==null){
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"该用户不存在");
        }
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",user.getId());
        List<UserRole> userRoleList = userRoleMapper.selectList(wrapper);
        List<Long> roleIds=new ArrayList<>();
        if(!CollectionUtils.isEmpty(userRoleList)){
            for (UserRole userRole : userRoleList) {
                Role role = roleMapper.selectById(userRole.getRoleId());
                if(role!=null){
                    roleIds.add(role.getId());
                }
            }
        }
        return roleIds;
    }

    @Override
    public List<User> findAll() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        return userMapper.selectList(wrapper);
    }
}
