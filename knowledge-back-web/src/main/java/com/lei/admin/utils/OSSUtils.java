package com.lei.admin.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

/**
 * @Author LEI
 * @Date 2021/3/15 10:27
 * @Description TODO
 */
public class OSSUtils {
    private static String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    private static String accessKeyId = "LTAI4GBPb72afQcKt9zGQwr8";
    private static String accessKeySecret = "FlQTTGwNPQZaSyC3JeCO7MiQ1x4MT5";

    public static OSS getOssClient() {
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }




}
