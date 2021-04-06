package com.lei.admin.controller;


import com.lei.admin.entity.Tag;
import com.lei.admin.service.ITagService;
import com.lei.admin.vo.*;
import com.lei.error.SystemException;
import com.lei.response.ResponseModel;
import com.lei.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-03-25
 */
@RestController
@RequestMapping("/admin/tag")
@Api(tags = "标签相关接口")
public class TagController {

    @Autowired
    private ITagService tagService;

    @GetMapping("/listAll")
    @ApiOperation("获取所有tag数据")
    public ResponseModel<PageUtils<Tag>> listAll(@RequestParam(value = "pageNum", defaultValue = "1")Integer pageNum,
                                                 @RequestParam(value = "pageSize", defaultValue = "6")Integer pageSize) {
        PageUtils<Tag> result = tagService.listAll(pageNum,pageSize);
        return ResponseModel.success(result);
    }

    @PostMapping("/add")
    @ApiOperation("添加标签")
    public ResponseModel add(@RequestBody TagVO tagVO) {
        tagService.add(tagVO);
        return ResponseModel.success();
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除标签")
    public ResponseModel delete(@PathVariable Integer id) {
        tagService.delete(id);
        return ResponseModel.success();
    }

    @PutMapping("/edit/{id}")
    @ApiOperation("编辑标签")
    public ResponseModel edit(@PathVariable Integer id, @RequestBody TagVO tagVO) throws SystemException {
        tagService.edit(id,tagVO);
        return ResponseModel.success();
    }

    @PostMapping("/addFileTag")
    @ApiOperation("插入文件-标签关系")
    public ResponseModel addFileTag(@RequestBody FileTagVO fileTagVO) {
        tagService.addFileTag(fileTagVO);
        return ResponseModel.success();
    }


    @PostMapping("/addArticleTag")
    @ApiOperation("插入文章-标签关系")
    public ResponseModel addArticleTag(@RequestBody TinymceTagVO tinymceTagVO) {
        tagService.addArticleTag(tinymceTagVO);
        return ResponseModel.success();
    }

    @GetMapping("/list")
    @ApiOperation("获取所有tag数据")
    public ResponseModel<List<Tag>> list() {
        List<Tag> result = tagService.listAll();
        return ResponseModel.success(result);
    }

    @GetMapping("/listWordCloudData")
    @ApiOperation("获取词云数据")
    public ResponseModel<List<WordCloudDTO>> listWordCloudData() {
        List<WordCloudDTO> result = tagService.listWordCloudData();
        return ResponseModel.success(result);
    }

    @GetMapping("/getTopTag")
    @ApiOperation("获取排名最高的tag")
    public ResponseModel<List<Tag>> getTopTag() {
        List<Tag> result = tagService.getTopTag();
        return ResponseModel.success(result);
    }





}

