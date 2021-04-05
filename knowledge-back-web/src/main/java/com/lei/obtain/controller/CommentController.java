package com.lei.obtain.controller;


import com.lei.admin.vo.ClassificationNodeVO;
import com.lei.obtain.entity.Comment;
import com.lei.obtain.service.ICommentService;
import com.lei.obtain.vo.CommentNodeVO;
import com.lei.response.ResponseModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-04-02
 */
@RestController
@RequestMapping("/obtain/comment")
public class CommentController {

    @Autowired
    private ICommentService commentService;

    @ApiOperation("加载评论数据")
    @GetMapping("/tree")
    public ResponseModel<Map<String,Object>> tree(@RequestParam("articleId")Integer articleId) {
        List<CommentNodeVO> commentNodeVOList = commentService.tree(articleId);
        Map<String, Object> map = new HashMap<>();
        map.put("tree", commentNodeVOList);
        return ResponseModel.success(map);
    }

    @ApiOperation("增加评论")
    @PostMapping("/add")
    public ResponseModel add(@RequestBody Comment comment) {
        commentService.add(comment);
        return ResponseModel.success();
    }


}

