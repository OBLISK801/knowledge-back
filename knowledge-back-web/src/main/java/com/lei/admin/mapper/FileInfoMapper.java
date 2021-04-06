package com.lei.admin.mapper;

import com.lei.admin.entity.FileInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lei.admin.vo.DownLoadVO;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-03-06
 */
@Repository
public interface FileInfoMapper extends BaseMapper<FileInfo> {
    List<FileInfo> findFileList(@Param("fileName") String fileName,
                                @Param("fileType") String fileType,
                                @Param("classificationId") Integer classificationId,
                                @Param("totalSize") Integer totalSize,
                                @Param("beginDate") Date beginDate,
                                @Param("endDate") Date endDate,
                                @Param("username") String username,
                                @Param("isPublic") Integer isPublic);

    List<FileInfo> findPublicFile(@Param("tagId")Integer tagId,
                                  @Param("classificationId")Integer classificationId);

    List<FileInfo> findByTagId(@Param("tagId") Integer tagId);

    List<DownLoadVO> findByUser(@Param("username")String username);

}
