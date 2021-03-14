package com.lei.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lei.enums.RoleStatusEnum;
import com.lei.error.SystemCodeEnum;
import com.lei.error.SystemException;
import com.lei.system.converter.RoleConverter;
import com.lei.system.entity.Menu;
import com.lei.system.entity.Role;
import com.lei.system.entity.RoleMenu;
import com.lei.system.mapper.MenuMapper;
import com.lei.system.mapper.RoleMapper;
import com.lei.system.mapper.RoleMenuMapper;
import com.lei.system.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lei.system.vo.RoleVO;
import com.lei.utils.PageUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-02-12
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public void authority(Long id, Long[] mids) throws SystemException {
        Role role = roleMapper.selectById(id);
        if(role==null){
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"该角色不存在");
        }
        //先删除原来的权限
        QueryWrapper<RoleMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",id);
        roleMenuMapper.delete(wrapper);
        //增加现在分配的角色
        if(mids.length>0){
            for (Long mid : mids) {
                Menu menu = menuMapper.selectById(mid);
                if(menu==null){
                    throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"menuId="+mid+",菜单权限不存在");
                }else {
                    RoleMenu roleMenu = new RoleMenu();
                    roleMenu.setRoleId(id);
                    roleMenu.setMenuId(mid);
                    roleMenuMapper.insert(roleMenu);
                }
            }
        }
    }

    @Override
    public List<Long> findMenuIdsByRoleId(Long id) throws SystemException {
        Role role = roleMapper.selectById(id);
        if(role==null){
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"该角色已不存在");
        }
        List<Long> ids=new ArrayList<>();
        QueryWrapper<RoleMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",id);
        List<RoleMenu> roleMenus = roleMenuMapper.selectList(wrapper);
        if(!CollectionUtils.isEmpty(roleMenus)){
            for (RoleMenu roleMenu : roleMenus) {
                ids.add(roleMenu.getMenuId());
            }
        }
        return ids;
    }

    @Override
    public PageUtils<RoleVO> findRoleList(Integer pageNum, Integer pageSize, RoleVO roleVO) {
        String roleName = roleVO.getRoleName();
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.like("role_name",roleName);
        List<Role> roles = roleMapper.selectList(wrapper);
        List<RoleVO> roleVOS= RoleConverter.converterToRoleVOList(roles);
        PageUtils<RoleVO> info = new PageUtils<>(pageNum,pageSize);
        info.doPage(roleVOS);
        return info;
    }

    @Override
    public void add(RoleVO roleVO) throws SystemException {
        String roleName = roleVO.getRoleName();
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("role_name",roleName);
        int i = roleMapper.selectCount(wrapper);
        if(i!=0){
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"该角色名已被占用");
        }
        Role role = new Role();
        BeanUtils.copyProperties(roleVO,role);
        role.setCreateTime(new Date());
        role.setModifiedTime(new Date());
        role.setStatus(RoleStatusEnum.AVAILABLE.getStatusCode());//默认启用添加的角色
        roleMapper.insert(role);
    }

    @Override
    public void deleteById(Long id) throws SystemException {
        Role role = roleMapper.selectById(id);
        if(role==null){
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"要删除的角色不存在");
        }
        roleMapper.deleteById(id);
        //删除对应的[角色-菜单]记录
        QueryWrapper<RoleMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",id);
        roleMenuMapper.delete(wrapper);
    }

    @Override
    public RoleVO edit(Long id) throws SystemException {
        Role role = roleMapper.selectById(id);
        if(role==null){
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"编辑的角色不存在");
        }
        RoleVO roleVO = new RoleVO();
        BeanUtils.copyProperties(role,roleVO);
        return roleVO;
    }

    @Override
    public void update(Long id, RoleVO roleVO) throws SystemException {
        String roleName = roleVO.getRoleName();
        Role dbRole = roleMapper.selectById(id);
        if(dbRole==null){
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"要更新的角色不存在");
        }
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("role_name",roleName);
        List<Role> roles = roleMapper.selectList(wrapper);
        if(!CollectionUtils.isEmpty(roles)){
            Role role = roles.get(0);
            if(!role.getId().equals(id)){
                throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"该角色名已被占用");
            }
        }
        Role role = new Role();
        BeanUtils.copyProperties(roleVO,role);
        role.setModifiedTime(new Date());
        UpdateWrapper<Role> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        roleMapper.update(role,updateWrapper);
    }

    @Override
    public void updateStatus(Long id, Boolean status) throws SystemException {
        Role role = roleMapper.selectById(id);
        if(role==null){
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"角色不存在");
        }
        Role t = new Role();
        t.setId(id);
        t.setStatus(status?RoleStatusEnum.DISABLE.getStatusCode():
                RoleStatusEnum.AVAILABLE.getStatusCode());
        UpdateWrapper<Role> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",id);
        roleMapper.update(t,wrapper);
    }

    @Override
    public List<Role> findAll() {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        return roleMapper.selectList(wrapper);
    }
}
