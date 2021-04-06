package com.lei.admin.controller;


import com.lei.admin.entity.ChunkInfo;
import com.lei.admin.entity.DownloadInfo;
import com.lei.admin.entity.FileInfo;
import com.lei.admin.service.IChunkInfoService;
import com.lei.admin.service.IDownloadInfoService;
import com.lei.admin.service.IFileInfoService;
import com.lei.admin.vo.ChunkResult;
import com.lei.admin.vo.DownLoadVO;
import com.lei.admin.vo.FileInfoVO;
import com.lei.error.SystemException;
import com.lei.obtain.service.IFieryCountService;
import com.lei.response.ResponseModel;
import com.lei.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-03-06
 */
@RestController
@RequestMapping("/admin/file")
@Api(tags = "文件相关接口")
public class FileInfoController {

    @Autowired
    private IChunkInfoService chunkInfoService;
    @Autowired
    private IFileInfoService fileInfoService;
    @Autowired
    private IFieryCountService fieryCountService;
    @Autowired
    private IDownloadInfoService downloadInfoService;

    /**
     * 200, 201, 202: 当前块上传成功，不需要重传。
     * 404, 415. 500, 501: 当前块上传失败，会取消整个文件上传。
     * 其他状态码: 出错了，但是会自动重试上传。
     *
     * @param chunk
     * @param response
     * @return
     */

    @GetMapping("/chunk")
    @ApiOperation("上传文件前的get请求")
    public ChunkResult checkChunk(ChunkInfo chunk, HttpServletResponse response) {
        return chunkInfoService.checkChunkState(chunk, response);
    }

    @PostMapping("/chunk")
    @ApiOperation("文件上传")
    public Integer uploadChunk(ChunkInfo chunk) {
        return chunkInfoService.uploadFile(chunk);
    }

    @PostMapping("/mergeFile")
    @ApiOperation("文件合并")
    public HttpServletResponse mergeFile(@RequestBody FileInfo fileInfo, HttpServletResponse response) {
        return fileInfoService.mergeFile(fileInfo, response);
    }

    @GetMapping("/findFileList")
    @ApiOperation("查询文件列表")
    public ResponseModel<PageUtils<FileInfo>> findFileList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                           @RequestParam(value = "pageSize", defaultValue = "7") Integer pageSize,
                                                           @RequestParam(value = "username") String username,
                                                           FileInfoVO fileInfoVO) {
        PageUtils<FileInfo> result = fileInfoService.findFileList(pageNum, pageSize, username, fileInfoVO);
        return ResponseModel.success(result);
    }

    @DeleteMapping("/deleteFile")
    @ApiOperation("删除文件")
    public ResponseModel deleteFile(@RequestBody FileInfo fileInfo) throws SystemException {
        fileInfoService.deleteFile(fileInfo);
        return ResponseModel.success();
    }

    @PostMapping("/download")
    @ApiOperation("下载文件")
    public void download(@RequestBody FileInfo info,
                         HttpServletResponse response) throws IOException {
        FileInfo fileInfo = fileInfoService.findById(info.getId());
        //下载次数+1
        //fieryCountService.addCount(info.getId(), 1, info.getUploadUser(), 1);
        //获取文件
        File file = new File(fileInfo.getLocation(), fileInfo.getFileName());
        //获取文件输入流
        FileInputStream is = new FileInputStream(file);
        //attachment;
        response.setHeader("content-disposition", "attachment;fileName=" + fileInfo.getFileName());
        //获取响应输出流
        ServletOutputStream os = response.getOutputStream();
        //文件拷贝
        IOUtils.copy(is, os);
        //关流方式(优雅)
        IOUtils.closeQuietly(is);
        IOUtils.closeQuietly(os);
    }

    @GetMapping("/getUrl")
    @ApiOperation("获取资源链接")
    public ResponseModel<FileInfo> getUrl(@RequestParam("id") Integer id,
                                          @RequestParam("userName") String userName) {
        FileInfo fileInfo = fileInfoService.findById(id);
        // 预览数+1
        fieryCountService.addCount(id, 2, userName, 1);
        return ResponseModel.success(fileInfo);
    }

    @GetMapping("/preview")
    @ApiOperation("使用openoffice转换office文档为pdf，进行在线显示")
    public ResponseModel<String> preview(@RequestParam("id") Integer id) {
        String previewUrl = fileInfoService.preview(id);
        return ResponseModel.success(previewUrl);
    }

    @PutMapping("/public/{id}")
    @ApiOperation("公开资源")
    public ResponseModel publicMyFile(@PathVariable Integer id) {
        fileInfoService.publicMyFile(id);
        return ResponseModel.success();
    }

    @GetMapping("/findPublicFile")
    @ApiOperation("检索公共资源")
//    @RequestParam(value = "tagId") Integer tagId,
//    @RequestParam(value = "classificationId") Integer classificationId
    public ResponseModel<PageUtils<FileInfo>> findPublicFile(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                             @RequestParam(value = "pageSize", defaultValue = "7") Integer pageSize) {
        PageUtils<FileInfo> result = fileInfoService.findPublicFile(pageNum, pageSize);
        return ResponseModel.success(result);
    }

    @PostMapping("/downloadFile")
    @ApiOperation("下载文件")
    public void download(@RequestBody DownloadInfo downloadInfo, HttpServletResponse response) throws IOException {
        FileInfo fileInfo = fileInfoService.findById(downloadInfo.getFileId());
        //获取文件
        File file = new File(fileInfo.getLocation(), fileInfo.getFileName());
        //获取文件输入流
        FileInputStream is = new FileInputStream(file);
        //attachment;
        response.setHeader("content-disposition", "attachment;fileName=" + fileInfo.getFileName());
        //获取响应输出流
        ServletOutputStream os = response.getOutputStream();
        //文件拷贝
        IOUtils.copy(is, os);
        //关流方式(优雅)
        IOUtils.closeQuietly(is);
        IOUtils.closeQuietly(os);
        downloadInfoService.add(downloadInfo);
    }

    @GetMapping("/findByTagId/{id}")
    @ApiOperation("根据标签获取文件列表")
    public ResponseModel<PageUtils<FileInfo>> findByTagId(@PathVariable Integer id,
                                                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                          @RequestParam(value = "pageSize", defaultValue = "7") Integer pageSize) {
        PageUtils<FileInfo> result = fileInfoService.findByTagId(id, pageNum, pageSize);
        return ResponseModel.success(result);
    }

    @GetMapping("/findByClassId/{id}")
    @ApiOperation("根据分类获取文件列表")
    public ResponseModel<PageUtils<FileInfo>> findByClassId(@PathVariable Integer id,
                                                            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                            @RequestParam(value = "pageSize", defaultValue = "7") Integer pageSize) {
        PageUtils<FileInfo> result = fileInfoService.findByClassId(id, pageNum, pageSize);
        return ResponseModel.success(result);
    }

    @GetMapping("/findByUser")
    @ApiOperation("获取用户下载的资源")
    public ResponseModel<PageUtils<DownLoadVO>> findByUser(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                           @RequestParam(value = "pageSize", defaultValue = "7") Integer pageSize,
                                                           @RequestParam(value = "username") String username) {
        PageUtils<DownLoadVO> result = fileInfoService.findByUser(pageNum, pageSize, username);
        return ResponseModel.success(result);
    }


}

