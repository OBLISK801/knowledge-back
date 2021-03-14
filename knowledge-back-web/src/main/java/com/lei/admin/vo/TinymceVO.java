package com.lei.admin.vo;

import lombok.Data;

/**
 * @Author LEI
 * @Date 2021/3/10 15:51
 * @Description TODO
 */
@Data
public class TinymceVO {
    private Integer id;
    private Integer classificationId;
    private String writeUser;
    private String title;
    private String summary;
}
