package com.lei.obtain.controller;


import com.lei.admin.entity.FileInfo;
import com.lei.admin.entity.Tinymce;
import com.lei.admin.service.IChunkInfoService;
import com.lei.obtain.service.IFieryCountService;
import com.lei.obtain.vo.TopFileInfoVO;
import com.lei.obtain.vo.TopTinymceVO;
import com.lei.response.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-03-14
 */
@RestController
@RequestMapping("/obtain/fiery")
@Api(tags = "热门资源排序相关接口")
public class FieryCountController {

    @Autowired
    private IFieryCountService fieryCountService;

    @GetMapping("/getTopTinymce")
    @ApiOperation("获取热门文章资源")
    public ResponseModel<List<TopTinymceVO>> getTopTinymce() {
        List<TopTinymceVO> list = fieryCountService.getTopTinymce();
        return ResponseModel.success(list);
    }

    @GetMapping("/getTopFileInfo")
    @ApiOperation("获取热门文件资源")
    public ResponseModel<List<TopFileInfoVO>> getTopFileInfo() {
        List<TopFileInfoVO> list = fieryCountService.getTopFileInfo();
        return ResponseModel.success(list);
    }

}

