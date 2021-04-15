package com.lei.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @Author LEI
 * @Date 2021/4/15 8:39
 * @Description TODO
 */
@Data
public class RecentlyReadVO {

    private Integer resourceId;

    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss ", timezone = "GMT+8")
    private Date happenTime;

    private String writeUser;

    private List<String> tagName;
}
