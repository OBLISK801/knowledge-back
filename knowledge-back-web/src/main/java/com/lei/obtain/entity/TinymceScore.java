package com.lei.obtain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-04-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TinymceScore对象", description="")
public class TinymceScore implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "打分人")
    private String username;

    @ApiModelProperty(value = "分数")
    private Integer score;

    @ApiModelProperty(value = "被打分文章")
    private Integer tinymceId;

    @ApiModelProperty(value = "打分时间")
    private Date time;


}
