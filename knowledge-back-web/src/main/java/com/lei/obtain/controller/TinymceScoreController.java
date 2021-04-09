package com.lei.obtain.controller;


import com.lei.admin.entity.Tinymce;
import com.lei.admin.vo.ArticleAllVO;
import com.lei.obtain.service.ITinymceScoreService;
import com.lei.obtain.vo.ScoreVO;
import com.lei.response.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-04-07
 */
@RestController
@RequestMapping("/obtain/score")
@Api(tags = "打分相关接口")
public class TinymceScoreController {

    @Autowired
    private ITinymceScoreService tinymceScoreService;

    @ApiOperation("打分")
    @PostMapping("/score")
    public ResponseModel score(@RequestBody ScoreVO scoreVO) {
        tinymceScoreService.score(scoreVO);
        tinymceScoreService.addUserScore(scoreVO);
        return ResponseModel.success();
    }

    @ApiOperation("")
    @GetMapping("/getScore")
    public ResponseModel<Integer> getScore(@RequestParam("tinymceId") Integer tinymceId,
                                           @RequestParam("username") String username) {
        Integer score = tinymceScoreService.getScore(tinymceId, username);
        return ResponseModel.success(score);
    }

    @ApiOperation("获取推荐文章")
    @GetMapping("/getRecommenderArticle")
    public ResponseModel<List<ArticleAllVO>> getRecommenderArticle(@RequestParam("username")String username) throws ClassNotFoundException, TasteException {
        List<ArticleAllVO> result = tinymceScoreService.getRecommenderArticle(username);
        return ResponseModel.success(result);
    }
}

