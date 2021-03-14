package com.lei.admin.service;

import com.lei.admin.entity.FileInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lei.admin.vo.FileInfoVO;
import com.lei.error.SystemException;
import com.lei.utils.PageUtils;
import io.swagger.models.auth.In;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-03-06
 */
public interface IFileInfoService extends IService<FileInfo> {

    HttpServletResponse mergeFile(FileInfo  fileInfo, HttpServletResponse response);

    PageUtils<FileInfo> findFileList(Integer pageNum, Integer pageSize, FileInfoVO fileInfoVO);

    void deleteFile(FileInfo fileInfo) throws SystemException;

    FileInfo findById(Integer id);

    String preview(Integer id);

}
