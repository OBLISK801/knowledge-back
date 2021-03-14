package com.lei.system.service;

import com.lei.error.SystemException;
import com.lei.system.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lei.system.vo.MenuNodeVO;
import com.lei.system.vo.MenuVO;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-02-12
 */
public interface IMenuService extends IService<Menu> {

    List<MenuNodeVO> findMenuTree();
    // 所有展开菜单ID
    List<Long> findOpenIds();

    Menu add(MenuVO menuVO);

    void delete(Long id) throws SystemException;

    MenuVO edit(Long id) throws SystemException;

    void update(Long id, MenuVO menuVO) throws SystemException;
}
