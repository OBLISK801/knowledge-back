package com.lei.obtain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author LEI
 * @Date 2021/3/15 14:53
 * @Description TODO
 */
@Data
public class TopFileInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "文件标识")
    private String identifier;

    @ApiModelProperty(value = "总大小")
    private Integer totalSize;

    @ApiModelProperty(value = "存储地址")
    private String location;

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @ApiModelProperty(value = "文件所属分类")
    private Integer classificationId;

    @ApiModelProperty(value = "上传用户")
    private String uploadUser;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss ", timezone = "GMT+8")
    @ApiModelProperty(value = "上传时间")
    private Date uploadTime;

    private Integer clickCount;
}
