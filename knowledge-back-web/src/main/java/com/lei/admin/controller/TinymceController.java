package com.lei.admin.controller;


import com.lei.admin.entity.Tinymce;
import com.lei.admin.service.ITinymceService;
import com.lei.admin.vo.ArticleAllVO;
import com.lei.admin.vo.ArticleVO;
import com.lei.admin.vo.FileInfoVO;
import com.lei.admin.vo.TinymceVO;
import com.lei.obtain.service.IFieryCountService;
import com.lei.response.ResponseModel;
import com.lei.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.service.ResponseMessage;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-03-09
 */
@RestController
@RequestMapping("/admin/tinymce")
@Api(tags = "富文本编辑器相关接口")
public class TinymceController {

    @Autowired
    private ITinymceService tinymceService;
    @Autowired
    private IFieryCountService fieryCountService;

    @PostMapping("/save")
    @ApiOperation("暂时存储富文本内容")
    public ResponseModel<Integer> save(@RequestBody Tinymce tinymce) {
        Integer id = tinymceService.saveContent(tinymce);
        return ResponseModel.success(id);
    }

    @GetMapping("/listContent")
    @ApiOperation("获取草稿")
    public ResponseModel<ArticleVO> listContent(@RequestParam("writeUser") String writeUser,
                                                @RequestParam("isArticle")Integer isArticle) {
        ArticleVO articleVO = tinymceService.listContent(writeUser,isArticle);
        return ResponseModel.success(articleVO);
    }

    @PostMapping("/complete")
    @ApiOperation("保存文章")
    public ResponseModel<Integer> complete(@RequestBody Tinymce tinymce) {
        Integer id = tinymceService.complete(tinymce);
        return ResponseModel.success(id);
    }

    @GetMapping("/listAll")
    @ApiOperation("获取所有文章")
    public ResponseModel<PageUtils<ArticleVO>> listAll(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                     @RequestParam(value = "isArticle",defaultValue = "0") Integer isArticle,
                                                     TinymceVO tinymceVO) {
        PageUtils<ArticleVO> result = tinymceService.listAll(pageNum,pageSize,isArticle,tinymceVO);
        return ResponseModel.success(result);
    }

    @GetMapping("/listById")
    @ApiOperation("获取草稿")
    public ResponseModel<ArticleVO> listById(@RequestParam("tinymceId") Integer tinymceId,
                                             @RequestParam("userName") String userName) {
        ArticleVO articleVO = tinymceService.listById(tinymceId);
        //
        fieryCountService.addCount(tinymceId,2,userName,2);
        return ResponseModel.success(articleVO);
    }

    @PostMapping("/edit")
    @ApiOperation("编辑文章")
    public ResponseModel edit(@RequestBody Tinymce tinymce) {
        tinymceService.edit(tinymce);
        //
        fieryCountService.addCount(tinymce.getId(),2,tinymce.getWriteUser(),2);
        return ResponseModel.success();
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除文章")
    public ResponseModel delete(@RequestParam(value = "id",defaultValue = "")Integer id) {
        tinymceService.delete(id);
        return ResponseModel.success();
    }

    @GetMapping("/getArticleById")
    @ApiOperation("获取文章相关知识")
    public ResponseModel<PageUtils<ArticleAllVO>> getArticleById(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                             @RequestParam(value = "classificationId") Integer classificationId) {
        PageUtils<ArticleAllVO> result = tinymceService.getArticleById(pageNum,pageSize,classificationId);
        return ResponseModel.success(result);
    }

    @GetMapping("/listDetails")
    @ApiOperation("获取草稿")
    public ResponseModel<ArticleAllVO> listDetails(@RequestParam("tinymceId") Integer tinymceId,
                                                   @RequestParam("userName") String userName) {
        ArticleAllVO articleVO = tinymceService.listDetails(tinymceId);
        //
        fieryCountService.addCount(tinymceId,2,userName,2);
        return ResponseModel.success(articleVO);
    }

    @GetMapping("/getArticle")
    @ApiOperation("获取文章相关知识")
    public ResponseModel<PageUtils<ArticleAllVO>> getArticle(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageUtils<ArticleAllVO> result = tinymceService.getArticle(pageNum,pageSize);
        return ResponseModel.success(result);
    }




}

