package com.lei.system.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Author LEI
 * @Date 2021/2/16 20:39
 * @Description 创建 JWTToken 替换 Shiro 原生 Token
 */
public class JWTToken implements AuthenticationToken {

    //密钥
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
