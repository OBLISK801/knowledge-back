package com.lei.admin.controller;


import com.lei.admin.entity.Classification;
import com.lei.admin.service.IClassificationService;
import com.lei.admin.vo.ClassificationNodeVO;
import com.lei.admin.vo.GraphDataVO;
import com.lei.admin.vo.GraphLinksVO;
import com.lei.error.SystemException;
import com.lei.response.ResponseModel;
import com.lei.system.entity.Menu;
import com.lei.system.vo.MenuNodeVO;
import com.lei.system.vo.MenuVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-02-25
 */
@RestController
@RequestMapping("/admin/classification")
@Api(tags = "知识分类相关接口")
public class ClassificationController {

    @Autowired
    private IClassificationService classificationService;

    @ApiOperation("加载分类数据")
    @GetMapping("/tree")
    public ResponseModel<Map<String,Object>> tree() {
        List<ClassificationNodeVO> classificationTree = classificationService.tree();
        Map<String, Object> map = new HashMap<>();
        map.put("tree", classificationTree);
        return ResponseModel.success(map);
    }

    @ApiOperation("新增")
    @PostMapping("/add")
    public ResponseModel<Map<String, Object>> add(@RequestBody ClassificationNodeVO classificationNodeVO) {
        Classification node = classificationService.add(classificationNodeVO);
        Map<String, Object> map = new HashMap<>();
        map.put("id", node.getId());
        map.put("classificationName", node.getClassificationName());
        map.put("children", new ArrayList<>());
        return ResponseModel.success(map);
    }

    @ApiOperation("删除")
    @DeleteMapping("/delete/{id}")
    public ResponseModel delete(@PathVariable Integer id) throws SystemException {
        classificationService.delete(id);
        return ResponseModel.success();
    }

    @ApiOperation("编辑")
    @GetMapping("/edit/{id}")
    public ResponseModel<ClassificationNodeVO> edit(@PathVariable Integer id) throws SystemException {
        ClassificationNodeVO classificationNodeVO = classificationService.edit(id);
        return ResponseModel.success(classificationNodeVO);
    }

    @ApiOperation("更新")
    @PutMapping("/update/{id}")
    public ResponseModel update(@PathVariable Integer id, @RequestBody ClassificationNodeVO classificationNodeVO) throws SystemException {
        classificationService.update(id, classificationNodeVO);
        return ResponseModel.success();
    }

    @ApiOperation("获取关系图数据")
    @GetMapping("/getGraphData")
    public ResponseModel<Map<String,List<GraphDataVO>>> getGraphData() {
        List<GraphDataVO> graphDataVOS = classificationService.getGraphData();
        Map<String,List<GraphDataVO>> map = new HashMap<>();
        map.put("data",graphDataVOS);
        return ResponseModel.success(map);
    }

    @ApiOperation("获取关系图关系")
    @GetMapping("/getGraphLinks")
    public ResponseModel<Map<String,List<GraphLinksVO>>> getGraphLinks() {
        List<GraphLinksVO> graphLinksVOS = classificationService.getGraphLinks();
        Map<String,List<GraphLinksVO>> map = new HashMap<>();
        map.put("links",graphLinksVOS);
        return ResponseModel.success(map);
    }

    @ApiOperation("获取所有数据")
    @GetMapping("/listAll")
    public ResponseModel<List<Classification>> listAll() {
        List<Classification> result = classificationService.listAll();
        return ResponseModel.success(result);
    }

    @ApiOperation("获取叶子分类节点")
    @GetMapping("/listChildren")
    public ResponseModel<List<Classification>> listChildren() {
        List<Classification> result = classificationService.listChildren();
        return ResponseModel.success(result);
    }

}

