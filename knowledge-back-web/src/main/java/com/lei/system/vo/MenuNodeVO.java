package com.lei.system.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @Author LEI
 * @Date 2021/2/18 10:42
 * @Description TODO
 */
@Data
public class MenuNodeVO {

    private Long id;

    private Long parentId;

    private String menuName;

    private String url = null;

    private String icon;

    private Long orderNum;

    private Integer open;

    private boolean disabled;

    private String perms;

    private Integer type;


    private List<MenuNodeVO> children = new ArrayList<>();

    /*
     * 排序,根据order排序
     */
    public static Comparator<MenuNodeVO> order() {
        return (o1, o2) -> {
            if (!o1.getOrderNum().equals(o2.getOrderNum())) {
                return (int) (o1.getOrderNum() - o2.getOrderNum());
            }
            return 0;
        };
    }
}
