package com.lei.obtain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author LEI
 * @Date 2021/4/7 17:45
 * @Description TODO
 */
@Data
public class UserScore {

    private Long userId;

    private Integer tinymceId;

    private Integer score;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss ", timezone = "GMT+8")
    private Date time;
}
