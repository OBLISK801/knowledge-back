package com.lei.admin.controller;


import com.lei.admin.entity.Tinymce;
import com.lei.admin.service.ITinymceService;
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
    public ResponseModel save(@RequestBody Tinymce tinymce) {
        tinymceService.saveContent(tinymce);
        return ResponseModel.success();
    }

    @GetMapping("/listContent")
    @ApiOperation("获取草稿")
    public ResponseModel<Tinymce> listContent(@RequestParam("writeUser") String writeUser,
                                              @RequestParam("isArticle")Integer isArticle) {
        Tinymce tinymce = tinymceService.listContent(writeUser,isArticle);
        return ResponseModel.success(tinymce);
    }

    @PostMapping("/complete")
    @ApiOperation("保存文章")
    public ResponseModel complete(@RequestBody Tinymce tinymce) {
        tinymceService.complete(tinymce);
        return ResponseModel.success();
    }

    @GetMapping("/listAll")
    @ApiOperation("获取所有文章")
    public ResponseModel<PageUtils<Tinymce>> listAll(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                     @RequestParam(value = "isArticle",defaultValue = "0") Integer isArticle,
                                                     TinymceVO tinymceVO) {
        PageUtils<Tinymce> result = tinymceService.listAll(pageNum,pageSize,isArticle,tinymceVO);
        return ResponseModel.success(result);
    }

    @GetMapping("/listById")
    @ApiOperation("获取草稿")
    public ResponseModel<Tinymce> listById(@RequestParam("tinymceId") Integer tinymceId,
                                           @RequestParam("userName") String userName) {
        Tinymce tinymce = tinymceService.listById(tinymceId);
        //
        fieryCountService.addCount(tinymceId,2,userName,2);
        return ResponseModel.success(tinymce);
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


}

