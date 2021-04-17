package com.lei.obtain.controller;

import com.lei.admin.entity.Tinymce;
import com.lei.obtain.service.ISearchService;
import com.lei.response.ResponseModel;
import com.lei.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * @Author LEI
 * @Date 2021/3/16 12:46
 * @Description TODO
 */
@RestController
@RequestMapping("/elastics/tinymce")
public class SearchController {

    @Autowired
    private ISearchService searchService;

    @GetMapping("/search")
    public ResponseModel<PageUtils<Map<String,Object>>> search(@RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                                                               @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize,
                                                               @RequestParam(value = "state")String state) throws IOException {
        PageUtils<Map<String,Object>> result = searchService.search(pageNum,pageSize,state);
        return ResponseModel.success(result);
    }

    @GetMapping("/searchPdf")
    public ResponseModel<PageUtils<Map<String,Object>>> searchPdf(@RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                                                               @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize,
                                                               @RequestParam(value = "state")String state) throws IOException {
        PageUtils<Map<String,Object>> result = searchService.searchPdf(pageNum,pageSize,state);
        return ResponseModel.success(result);
    }
}
