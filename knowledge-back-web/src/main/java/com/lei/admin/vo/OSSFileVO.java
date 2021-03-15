package com.lei.admin.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author LEI
 * @Date 2021/3/15 11:22
 * @Description TODO
 */
@Data
public class OSSFileVO {
    private MultipartFile file;
    private String userName;
    private Integer classificationId;
}
