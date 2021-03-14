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
 * @since 2021-03-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="FieryCount对象", description="")
public class FieryCount implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "点击次数")
    private Integer clickCount;

    @ApiModelProperty(value = "下载次数")
    private Integer downloadCount;

    @ApiModelProperty(value = "预览次数")
    private Integer previewCount;

    @ApiModelProperty(value = "资源id")
    private Integer resourceId;

    @ApiModelProperty(value = "发生时间")
    private Date happenTime;

    @ApiModelProperty(value = "发生人")
    private String userName;

    @ApiModelProperty(value = "资源类型，1-文件，2-文章")
    private Integer resourceType;


}
