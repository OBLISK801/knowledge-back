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
    private static String accessKeyId = "xx";
    private static String accessKeySecret = "xx";

    public static OSS getOssClient() {
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }




}
