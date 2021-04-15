package com.lei.admin.service;

import com.lei.admin.vo.OSSFileVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Author LEI
 * @Date 2021/3/15 10:28
 * @Description TODO
 */
public interface IOSService {
    String uploadOSSFile(OSSFileVO ossFileVO) throws IOException;

    String uploadPhoto(MultipartFile file);
}
