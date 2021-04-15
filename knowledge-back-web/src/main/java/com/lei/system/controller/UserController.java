package com.lei.system.controller;


import com.lei.error.SystemException;
import com.lei.response.ResponseModel;
import com.lei.system.converter.RoleConverter;
import com.lei.system.entity.Role;
import com.lei.system.entity.User;
import com.lei.system.service.IRoleService;
import com.lei.system.service.IUserService;
import com.lei.system.vo.*;
import com.lei.utils.PageUtils;
import com.wuwenze.poi.ExcelKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-02-12
 */
@RestController
@RequestMapping("/system/user")
@Api(tags = "用户相关接口")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;

    @ApiOperation("用户登入")
    @PostMapping("/login")
    public ResponseModel<String> login(@RequestBody UserLoginVO userLoginVO) throws SystemException {
        String token = userService.login(userLoginVO.getUsername(), userLoginVO.getPassword());
        // 错误或失败由前端的catch来反馈给用户
        return ResponseModel.success(token);
    }

    @ApiOperation("用户信息")
    @GetMapping("/info")
    public ResponseModel<UserInfoVO> info() {
        UserInfoVO userInfoVO = userService.info();
        return ResponseModel.success(userInfoVO);
    }

    @ApiOperation("加载菜单")
    @GetMapping("findMenu")
    public ResponseModel<List<MenuNodeVO>> findMenu() {
        List<MenuNodeVO> menuTreeVOS = userService.findMenu();
        return ResponseModel.success(menuTreeVOS);
    }

    @ApiOperation("用户注册/添加")
    @PostMapping("/register")
    public ResponseModel register(@RequestBody UserVO userVO) throws SystemException {
        userService.register(userVO);
        return ResponseModel.success();
    }

    @ApiOperation("模糊查询用户列表")
    @GetMapping("/findUserList")
    public ResponseModel<PageUtils<UserVO>> findUserList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                         @RequestParam(value = "pageSize", defaultValue = "7") Integer pageSize,
                                                         UserVO userVO) {
        PageUtils<UserVO> userList = userService.findUserList(pageNum, pageSize, userVO);
        return ResponseModel.success(userList);
    }

    @ApiOperation("分配角色")
    @RequiresPermissions({"user:assign"})
    @PostMapping("/{id}/assignRoles")
    public ResponseModel assignRoles(@PathVariable Long id, @RequestBody Long[] rids) throws SystemException {
        userService.assignRoles(id, rids);
        return ResponseModel.success();
    }

    @RequiresPermissions({"user:delete"})
    @ApiOperation("删除用户")
    @DeleteMapping("/delete/{id}")
    public ResponseModel delete(@PathVariable Long id) throws SystemException {
        userService.deleteById(id);
        return ResponseModel.success();
    }

    @ApiOperation("禁用和启用状态")
    @RequiresPermissions({"user:status"})
    @PutMapping("/updateStatus/{id}/{status}")
    public ResponseModel updateStatus(@PathVariable Long id, @PathVariable Boolean status) throws SystemException {
        userService.updateStatus(id, status);
        return ResponseModel.success();
    }

    @ApiOperation("更新用户")
    @RequiresPermissions({"user:update"})
    @PutMapping("/update/{id}")
    public ResponseModel update(@PathVariable Long id, @RequestBody UserEditVO userEditVO) throws SystemException {
        userService.update(id, userEditVO);
        return ResponseModel.success();
    }

    @ApiOperation("编辑用户")
    @RequiresPermissions({"user:edit"})
    @GetMapping("/edit/{id}")
    public ResponseModel<UserEditVO> edit(@PathVariable Long id) throws SystemException {
        UserEditVO userVO = userService.edit(id);
        return ResponseModel.success(userVO);
    }

    @ApiOperation("添加用户")
    @RequiresPermissions({"user:add"})
    @PostMapping("/add")
    public ResponseModel add(@RequestBody UserVO userVO) throws SystemException {
        userService.register(userVO);
        return ResponseModel.success();
    }

    @ApiOperation("根据用户id，获取用户已经拥有的角色")
    @GetMapping("/{id}/roles")
    public ResponseModel<Map<String, Object>> roles(@PathVariable Long id) throws SystemException {
        List<Long> values = userService.roles(id);
        List<Role> list = roleService.findAll();
        //转成前端需要的角色Item
        List<RoleTransferItemVO> items = RoleConverter.converterToRoleTransferItem(list);
        Map<String, Object> map = new HashMap<>();
        map.put("roles", items);
        map.put("values", values);
        return ResponseModel.success(map);
    }

    @ApiOperation(value = "导出excel", notes = "导出所有用户的excel表格")
    @PostMapping("/excel")
    @RequiresPermissions("user:export")
    public void export(HttpServletResponse response) {
        List<User> users = this.userService.findAll();
        ExcelKit.$Export(User.class, response).downXlsx(users, false);
    }

    @PutMapping("/changeAvatar")
    public ResponseModel changeAvatar(@RequestBody AvatarVO avatarVO) {
        userService.changeAvatar(avatarVO);
        return ResponseModel.success();
    }

}

