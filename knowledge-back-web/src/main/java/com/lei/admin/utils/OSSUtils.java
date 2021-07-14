package com.lei.admin.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

/**
 * @Author LEI
 * @Date 2021/3/15 10:27
 * @Description TODO
 */
public class OSSUtils {
    

    public static OSS getOssClient() {
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }




}
