package com.lei.system.shiro;

import com.lei.system.entity.ActiveUser;
import com.lei.system.entity.Menu;
import com.lei.system.entity.Role;
import com.lei.system.entity.User;
import com.lei.system.jwt.JWTToken;
import com.lei.system.jwt.JWTUtils;
import com.lei.system.service.IUserService;
import lombok.SneakyThrows;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author LEI
 * @Date 2021/2/16 20:27
 * @Description 重写 Realm 的 supports() 方法是通过 JWT 进行登录判断的关键
 * 因为前文中创建了 JWTToken 用于替换 Shiro 原生 token
 * 所以必须在此方法中显式的进行替换，否则在进行判断时会一直失败
 * 登录的合法验证通常包括 token 是否有效 、用户名是否存在 、密码是否正确
 * 通过 JWTUtil 对前端传入的 token 进行处理获取到用户名
 * 然后使用用户名前往数据库获取到用户密码
 * 再将用户面传入 JWTUtil 进行验证即可
 */
@Service
public class ShiroDatabaseRealm extends AuthorizingRealm {

    @Autowired
    private IUserService userService;

    // 使用JWT替代原生Token
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    // 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        if (activeUser.getUser().getType() == 0) {
            authorizationInfo.addStringPermission("*:*");
        } else {
            List<String> permissions = new ArrayList<>(activeUser.getPermissions());
            List<Role> roleList = activeUser.getRoles();
            //授权角色
            if (!CollectionUtils.isEmpty(roleList)) {
                for (Role role : roleList) {
                    authorizationInfo.addRole(role.getRoleName());
                }
            }
            //授权权限
            if (!CollectionUtils.isEmpty(permissions)) {
                for (String permission : permissions) {
                    if (permission != null && !"".equals(permission)) {
                        authorizationInfo.addStringPermission(permission);
                    }
                }
            }
        }
        return authorizationInfo;
    }

    // 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
    @SneakyThrows
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtils.getUsername(token);

        if (username == null) {
            throw new AuthenticationException(" token错误，请重新登入！");
        }

        User userBean = userService.findUserByName(username);

        if (userBean == null) {
            throw new AccountException("账号不存在!");
        }

        if (JWTUtils.isExpire(token)) {
            throw new AuthenticationException(" token过期，请重新登入！");
        }

        if (!JWTUtils.verify(token, username, userBean.getPassword())) {
            throw new CredentialsException("密码错误!");
        }

        if (userBean.getStatus() == 0) {
            throw new LockedAccountException("账号已被锁定!");
        }

        //如果验证通过，获取用户的角色
        List<Role> roles = userService.findRolesById(userBean.getId());
        //查询用户的所有菜单(包括了菜单和按钮)
        List<Menu> menus = userService.findMenuByRoles(roles);

        Set<String> urls = new HashSet<>();
        Set<String> perms = new HashSet<>();
        if (!CollectionUtils.isEmpty(menus)) {
            for (Menu menu : menus) {
                String url = menu.getUrl();
                String per = menu.getPerms();
                if (menu.getType() == 0 && url != null && !"".equals(url)) {
                    urls.add(menu.getUrl());
                }
                if (menu.getType() == 1 && per != null && !"".equals(per)) {
                    perms.add(menu.getPerms());
                }
            }
        }
        //过滤出url,和用户的权限
        ActiveUser activeUser = new ActiveUser();
        activeUser.setRoles(roles);
        activeUser.setUser(userBean);
        activeUser.setMenus(menus);
        activeUser.setUrls(urls);
        activeUser.setPermissions(perms);
        return new SimpleAuthenticationInfo(activeUser, token, getName());
    }
}
