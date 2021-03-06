package com.lei.system.converter;

import com.lei.system.entity.Menu;
import com.lei.system.vo.MenuNodeVO;
import com.lei.system.vo.MenuVO;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author LEI
 * @Date 2021/2/18 10:51
 * @Description TODO
 */
public class MenuConverter {
    /**
     * 转成menuVO(只包含菜单)List
     *
     * @param menus
     * @return
     */
    public static List<MenuNodeVO> converterToMenuNodeVO(List<Menu> menus) {
        //先过滤出用户的菜单
        List<MenuNodeVO> menuNodeVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(menus)) {
            for (Menu menu : menus) {
                if (menu.getType() == 0) {
                    MenuNodeVO menuNodeVO = new MenuNodeVO();
                    BeanUtils.copyProperties(menu, menuNodeVO);
                    menuNodeVO.setDisabled(menu.getAvailable() == 0);
                    menuNodeVOS.add(menuNodeVO);
                }
            }
        }
        return menuNodeVOS;
    }


    /**
     * 转成menuVO(菜单和按钮）
     *
     * @param menus
     * @return
     */
    public static List<MenuNodeVO> converterToALLMenuNodeVO(List<Menu> menus) {
        //先过滤出用户的菜单
        List<MenuNodeVO> menuNodeVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(menus)) {
            for (Menu menu : menus) {
                MenuNodeVO menuNodeVO = new MenuNodeVO();
                BeanUtils.copyProperties(menu, menuNodeVO);
                menuNodeVO.setDisabled(menu.getAvailable() == 0);
                menuNodeVOS.add(menuNodeVO);
            }
        }
        return menuNodeVOS;
    }

    /**
     * 转成menuVO(菜单和按钮）
     *
     * @param menu
     * @return
     */
    public static MenuVO converterToMenuVO(Menu menu) {
        MenuVO menuVO = new MenuVO();
        if (menu != null) {
            BeanUtils.copyProperties(menu, menuVO);
            menuVO.setDisabled(menu.getAvailable() == 0);
        }
        return menuVO;
    }
}
