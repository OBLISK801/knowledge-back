package com.lei.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lei.error.SystemCodeEnum;
import com.lei.error.SystemException;
import com.lei.system.converter.MenuConverter;
import com.lei.system.entity.Menu;
import com.lei.system.mapper.MenuMapper;
import com.lei.system.service.IMenuService;
import com.lei.system.utils.MenuTreeBuilder;
import com.lei.system.vo.MenuNodeVO;
import com.lei.system.vo.MenuVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-02-12
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<MenuNodeVO> findMenuTree() {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        List<Menu> menus = menuMapper.selectList(wrapper);
        List<MenuNodeVO> menuNodeVOS = MenuConverter.converterToALLMenuNodeVO(menus);
        return MenuTreeBuilder.build(menuNodeVOS);
    }

    @Override
    public List<Long> findOpenIds() {
        List<Long> ids = new ArrayList<>();
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        List<Menu> menus = menuMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(menus)) {
            for (Menu menu : menus) {
                if (menu.getOpen() == 1) {
                    ids.add(menu.getId());
                }
            }
        }
        return ids;
    }

    @Override
    public Menu add(MenuVO menuVO) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuVO, menu);
        menu.setCreateTime(new Date());
        menu.setModifiedTime(new Date());
        menu.setAvailable(menuVO.isDisabled() ? 0 : 1);
        menuMapper.insert(menu);
        return menu;
    }

    @Override
    public void delete(Long id) throws SystemException {
        Menu menu = menuMapper.selectById(id);
        if (menu == null) {
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR, "要删除的菜单不存在");
        }
        menuMapper.deleteById(id);
    }

    @Override
    public MenuVO edit(Long id) throws SystemException {
        Menu menu = menuMapper.selectById(id);
        if(menu==null){
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"该编辑的菜单不存在");
        }
        return MenuConverter.converterToMenuVO(menu);
    }

    @Override
    public void update(Long id, MenuVO menuVO) throws SystemException {
        Menu dbMenu = menuMapper.selectById(id);
        if(dbMenu==null){
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"要更新的菜单不存在");
        }
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuVO,menu);
        menu.setId(id);
        menu.setAvailable(menuVO.isDisabled()?0:1);
        menu.setModifiedTime(new Date());
        // ???
        UpdateWrapper<Menu> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",id);
        menuMapper.update(menu,wrapper);
    }
}
