package com.lei.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2021-03-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="FileTag对象", description="")
public class FileTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "文件唯一标签")
    private String identifier;

    @ApiModelProperty(value = "标签id")
    private Integer tagId;


}
