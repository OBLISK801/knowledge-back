package com.lei.admin.controller;


import com.lei.admin.entity.Tinymce;
import com.lei.admin.service.ITinymceService;
import com.lei.admin.vo.FileInfoVO;
import com.lei.admin.vo.TinymceVO;
import com.lei.response.ResponseModel;
import com.lei.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @PostMapping("/save")
    @ApiOperation("暂时存储富文本内容")
    public ResponseModel save(@RequestBody Tinymce tinymce) {
        tinymceService.saveContent(tinymce);
        return ResponseModel.success();
    }

    @GetMapping("/listContent")
    @ApiOperation("获取草稿")
    public ResponseModel<Tinymce> listContent(@RequestParam("writeUser") String writeUser) {
        Tinymce tinymce = tinymceService.listContent(writeUser);
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
                                                     TinymceVO tinymceVO) {
        PageUtils<Tinymce> result = tinymceService.listAll(pageNum,pageSize,tinymceVO);
        return ResponseModel.success(result);
    }

    @GetMapping("/listById")
    @ApiOperation("获取草稿")
    public ResponseModel<Tinymce> listById(@RequestParam("tinymceId") Integer tinymceId) {
        Tinymce tinymce = tinymceService.listById(tinymceId);
        return ResponseModel.success(tinymce);
    }

    @PostMapping("/edit")
    @ApiOperation("编辑文章")
    public ResponseModel edit(@RequestBody Tinymce tinymce) {
        tinymceService.edit(tinymce);
        return ResponseModel.success();
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除文章")
    public ResponseModel delete(@RequestParam(value = "id",defaultValue = "")Integer id) {
        tinymceService.delete(id);
        return ResponseModel.success();
    }


}

