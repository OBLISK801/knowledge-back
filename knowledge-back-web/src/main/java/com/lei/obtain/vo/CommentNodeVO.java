package com.lei.obtain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author LEI
 * @Date 2021/4/2 16:06
 * @Description TODO
 */
@Data
public class CommentNodeVO {

    private Integer id;

    private String username;

    private String avatar;

    private String comment;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss ", timezone = "GMT+8")
    private Date time;

    private Boolean inputShow;

    private String pname;

    private Integer toId;

    private Integer articleId;

    private List<CommentNodeVO> reply = new ArrayList<>();
}
