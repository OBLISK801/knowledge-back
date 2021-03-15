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
public class TopTinymceVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "知识归纳文件id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "0=暂存，1=完成")
    private Integer state;

    @ApiModelProperty(value = "暂存时文件内容")
    private String content;

    @ApiModelProperty(value = "所属知识分类id")
    private Integer classificationId;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss ", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss ", timezone = "GMT+8")
    private Date modifiedTime;

    @ApiModelProperty(value = "拥有者")
    private String writeUser;

    @ApiModelProperty(value = "文章名")
    private String title;

    @ApiModelProperty(value = "文章摘要")
    private String summary;

    private Integer clickCount;
}
