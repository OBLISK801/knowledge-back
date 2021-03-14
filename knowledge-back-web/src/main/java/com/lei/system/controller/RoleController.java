package com.lei.system.controller;


import com.lei.error.SystemException;
import com.lei.response.ResponseModel;
import com.lei.system.service.IMenuService;
import com.lei.system.service.IRoleService;
import com.lei.system.vo.MenuNodeVO;
import com.lei.system.vo.RoleVO;
import com.lei.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-02-12
 */
@RestController
@RequestMapping("/system/role")
@Api(tags = "角色相关接口")
public class RoleController {

    @Autowired
    private IRoleService roleService;
    @Autowired
    private IMenuService menuService;

    @ApiOperation("角色授权")
    @RequiresPermissions({"role:authority"})
    @PostMapping("/authority/{id}")
    public ResponseModel authority(@PathVariable Long id, @RequestBody Long[] mids) throws SystemException {
        roleService.authority(id, mids);
        return ResponseModel.success();
    }

    @ApiOperation("角色菜单")
    @GetMapping("/findRoleMenu/{id}")
    public ResponseModel<Map<String, Object>> findRoleMenu(@PathVariable Long id) throws SystemException {
        List<MenuNodeVO> tree = menuService.findMenuTree();
        //角色拥有的菜单id
        List<Long> mids = roleService.findMenuIdsByRoleId(id);
        List<Long> ids = menuService.findOpenIds();
        Map<String, Object> map = new HashMap<>();
        map.put("tree", tree);
        map.put("mids", mids);
        map.put("open", ids);
        return ResponseModel.success(map);
    }

    @ApiOperation("角色列表")
    @GetMapping("/findRoleList")
    public ResponseModel<PageUtils<RoleVO>> findRoleList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                         @RequestParam(value = "pageSize", defaultValue = "7") Integer pageSize,
                                                         RoleVO roleVO) {
        PageUtils<RoleVO> roleList = roleService.findRoleList(pageNum, pageSize, roleVO);
        return ResponseModel.success(roleList);
    }

    @ApiOperation("添加角色")
    @RequiresPermissions({"role:add"})
    @PostMapping("/add")
    public ResponseModel add(@RequestBody RoleVO roleVO) throws SystemException {
        roleService.add(roleVO);
        return ResponseModel.success();
    }

    @ApiOperation("删除角色")
    @RequiresPermissions({"role:delete"})
    @DeleteMapping("/delete/{id}")
    public ResponseModel delete(@PathVariable Long id) throws SystemException {
        roleService.deleteById(id);
        return ResponseModel.success();
    }

    @ApiOperation("编辑用户")
    @GetMapping("/edit/{id}")
    @RequiresPermissions({"role:edit"})
    public ResponseModel<RoleVO> edit(@PathVariable Long id) throws SystemException {
        RoleVO roleVO = roleService.edit(id);
        return ResponseModel.success(roleVO);
    }

    @ApiOperation("更新角色")
    @RequiresPermissions({"role:update"})
    @PutMapping("/update/{id}")
    public ResponseModel update(@PathVariable Long id, @RequestBody RoleVO roleVO) throws SystemException {
        roleService.update(id, roleVO);
        return ResponseModel.success();
    }

    @ApiOperation("更新状态")
    @RequiresPermissions({"role:status"})
    @PutMapping("/updateStatus/{id}/{status}")
    public ResponseModel updateStatus(@PathVariable Long id, @PathVariable Boolean status) throws SystemException {
        roleService.updateStatus(id, status);
        return ResponseModel.success();
    }
}

