package com.lei.obtain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @Author LEI
 * @Date 2021/4/4 13:06
 * @Description TODO
 */
@Data
public class FavoriteArticleVO {

    private Integer tinymceId;

    private String writeUser;

    private String title;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss ", timezone = "GMT+8")
    private Date time;

    private List<Integer> tags;
}
