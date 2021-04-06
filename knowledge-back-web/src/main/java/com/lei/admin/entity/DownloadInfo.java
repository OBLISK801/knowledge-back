package com.lei.admin.entity;

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
 * @since 2021-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="DownloadInfo对象", description="")
public class DownloadInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "被下载文件id")
    private Integer fileId;

    @ApiModelProperty(value = "下载用户")
    private String downloadUser;

    @ApiModelProperty(value = "下载时间")
    private Date downloadTime;

    @ApiModelProperty(value = "下载次数")
    private Integer count;


}
