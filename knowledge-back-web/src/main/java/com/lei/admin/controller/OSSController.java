package com.lei.admin.controller;

import com.lei.admin.service.IOSService;
import com.lei.admin.vo.OSSFileVO;
import com.lei.response.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author LEI
 * @Date 2021/3/15 10:27
 * @Description TODO
 */
@RestController
@RequestMapping("/admin/oss")
@Api(tags = "阿里云文件相关接口")
public class OSSController {

    @Autowired
    private IOSService osService;

    @PostMapping("/upload")
    @ApiOperation("上传")
    public ResponseModel<String> uploadOssFile(OSSFileVO ossFileVO) throws IOException {
        String url = osService.uploadOSSFile(ossFileVO);
        return ResponseModel.success(url);
    }

    @PostMapping("/uploadPhoto")
    @ApiOperation("上传图片")
    public ResponseModel<String> uploadPhoto(MultipartFile file) {
        String url = osService.uploadPhoto(file);
        return ResponseModel.success(url);
    }


}
