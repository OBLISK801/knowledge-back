package com.lei.admin.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author LEI
 * @Date 2021/3/29 11:50
 * @Description TODO
 */
@Data
public class ArticleVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer state;

    private String content;

    private Integer classificationId;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss ", timezone = "GMT+8")
    private Date createTime;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss ", timezone = "GMT+8")
    private Date modifiedTime;

    private String writeUser;

    private String title;

    private String summary;

    private Integer isArticle;

    private Integer isPublic;

    private List<Integer> tags;
}
