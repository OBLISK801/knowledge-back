package com.lei.obtain.controller;


import com.lei.obtain.entity.Comment;
import com.lei.obtain.service.ICommentService;
import com.lei.obtain.vo.CommentVO;
import com.lei.response.ResponseModel;
import com.lei.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-03-19
 */
@RestController
@RequestMapping("/obtain/comment")
@Api("评论相关接口")
public class CommentController {

    @Autowired
    private ICommentService commentService;


    @PostMapping("comment")
    @ApiOperation("添加文章评论")
    public ResponseModel comment(@RequestBody CommentVO commentVO) {
        commentService.comment(commentVO);
        return ResponseModel.success();
    }

    @GetMapping("getComment")
    @ApiOperation("获取某文章的所有评论")
    public ResponseModel<PageUtils<Comment>> getComment(@RequestParam("tinymceId") Integer tinymceId,
                                                          @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                          @RequestParam(value = "pageSize",defaultValue = "6") Integer pageSize) {
        PageUtils<Comment> results = commentService.getComment(tinymceId,pageNo,pageSize);
        return ResponseModel.success(results);
    }

}

