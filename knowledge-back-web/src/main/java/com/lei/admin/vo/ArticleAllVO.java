package com.lei.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @Author LEI
 * @Date 2021/3/31 10:22
 * @Description TODO
 */
@Data
public class ArticleAllVO {
    private Integer id;

    private String title;

    private Integer classificationId;

    private String summary;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss ", timezone = "GMT+8")
    private Date createTime;

    private String content;

    private String username;

    private String avatar;

    private Integer clickCount;

    private List<String> tagName;
}
