package com.lei.admin.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-03-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ChunkInfo对象")
public class ChunkInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "块编号，从1开始")
    private Integer chunkNumber;

    @ApiModelProperty(value = "块大小")
    private Integer chunkSize;

    @ApiModelProperty(value = "当前块大小")
    private Integer currentChunkSize;

    @ApiModelProperty(value = "文件标识")
    private String identifier;

    @ApiModelProperty(value = "总块数")
    private Integer totalChunks;

    @ApiModelProperty(value = "总大小")
    private Integer totalSize;

    @ApiModelProperty(value = "文件名")
    private String filename;

    @ApiModelProperty(value = "文件夹上传的时候文件的相对路径属性")
    private String relativePath;

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @TableField(exist = false)
    private MultipartFile file;

}
