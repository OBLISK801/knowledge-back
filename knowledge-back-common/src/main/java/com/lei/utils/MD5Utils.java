package com.lei.utils;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @Author LEI
 * @Date 2021/2/16 22:43
 * @Description 密码MD5加密工具类
 */
public class MD5Utils {
    public static String md5Encryption(String source, String salt) {
        // 加密算法
        String algorithmName = "MD5";
        // 加密次数
        int hashIterations = 1024;
        SimpleHash simpleHash = new SimpleHash(algorithmName, source, salt, hashIterations);
        return simpleHash + "";
    }
}
