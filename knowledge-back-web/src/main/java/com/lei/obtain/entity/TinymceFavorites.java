package com.lei.obtain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 *
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-04-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TinymceFavorites对象", description="")
public class TinymceFavorites implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "文章ID")
    private Integer tinymceId;

    @ApiModelProperty(value = "收藏人")
    private String username;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss ", timezone = "GMT+8")
    @ApiModelProperty(value = "收藏时间")
    private Date time;

    @ApiModelProperty(value = "收藏状态（0-取消收藏，1-收藏）")
    private Integer status;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss ", timezone = "GMT+8")
    @ApiModelProperty(value = "修改时间")
    private Date modifiedTime;


}
