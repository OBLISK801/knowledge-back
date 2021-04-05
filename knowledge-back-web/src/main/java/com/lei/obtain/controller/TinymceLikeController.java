package com.lei.obtain.controller;


import com.lei.obtain.entity.TinymceLike;
import com.lei.obtain.service.ITinymceLikeService;
import com.lei.obtain.vo.TinymceLikeVO;
import com.lei.response.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-04-04
 */
@RestController
@RequestMapping("/obtain/like")
@Api(tags = "点赞相关接口")
public class TinymceLikeController {

    @Autowired
    private ITinymceLikeService tinymceLikeService;

    @PostMapping("/likeArticle")
    @ApiOperation("点赞")
    public ResponseModel likeArticle(@RequestBody TinymceLikeVO tinymceLikeVO) {
        tinymceLikeService.likeArticle(tinymceLikeVO);
        return ResponseModel.success();
    }

    @PostMapping("/unLikeArticle")
    @ApiOperation("取消点赞")
    public ResponseModel unLikeArticle(@RequestBody TinymceLikeVO tinymceLikeVO) {
        tinymceLikeService.unLikeArticle(tinymceLikeVO);
        return ResponseModel.success();
    }

    @PostMapping("/isLikeArticle")
    @ApiOperation("判断是否点赞")
    public ResponseModel<Boolean> isLikeArticle(@RequestBody TinymceLikeVO tinymceLikeVO) {
        TinymceLike tinymceLike = tinymceLikeService.isLikeArticle(tinymceLikeVO);
        if (tinymceLike != null) {
            return ResponseModel.success(true);
        } else {
            return ResponseModel.success(false);
        }
    }
}

