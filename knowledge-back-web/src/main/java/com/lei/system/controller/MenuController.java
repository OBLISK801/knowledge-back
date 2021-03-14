package com.lei.system.controller;


import com.lei.error.SystemException;
import com.lei.response.ResponseModel;
import com.lei.system.entity.Menu;
import com.lei.system.service.IMenuService;
import com.lei.system.vo.MenuNodeVO;
import com.lei.system.vo.MenuVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-02-12
 */
@RestController
@RequestMapping("/system/menu")
@Api(tags = "菜单相关接口")
public class MenuController {

    @Autowired
    private IMenuService menuService;

    @ApiOperation("加载菜单树")
    @GetMapping("/tree")
    public ResponseModel<Map<String,Object>> tree() {
        List<MenuNodeVO> menuTree = menuService.findMenuTree();
        List<Long> ids = menuService.findOpenIds();
        Map<String, Object> map = new HashMap<>();
        map.put("tree", menuTree);
        map.put("open", ids);
        return ResponseModel.success(map);
    }

    @ApiOperation("新增菜单")
    @RequiresPermissions({"menu:add"})
    @PostMapping("/add")
    public ResponseModel<Map<String, Object>> add(@RequestBody MenuVO menuVO) {
        Menu node = menuService.add(menuVO);
        Map<String, Object> map = new HashMap<>();
        map.put("id", node.getId());
        map.put("menuName", node.getMenuName());
        map.put("children", new ArrayList<>());
        map.put("icon", node.getIcon());
        return ResponseModel.success(map);
    }

    @ApiOperation("删除菜单")
    @RequiresPermissions({"menu:delete"})
    @DeleteMapping("/delete/{id}")
    public ResponseModel delete(@PathVariable Long id) throws SystemException {
        menuService.delete(id);
        return ResponseModel.success();
    }

    @ApiOperation("编辑菜单")
    @RequiresPermissions({"menu:edit"})
    @GetMapping("/edit/{id}")
    public ResponseModel<MenuVO> edit(@PathVariable Long id) throws SystemException {
        MenuVO menuVO = menuService.edit(id);
        return ResponseModel.success(menuVO);
    }

    @ApiOperation("更新菜单")
    @RequiresPermissions({"menu:update"})
    @PutMapping("/update/{id}")
    public ResponseModel update(@PathVariable Long id, @RequestBody MenuVO menuVO) throws SystemException {
        menuService.update(id, menuVO);
        return ResponseModel.success();
    }
}

