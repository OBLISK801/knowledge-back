package com.lei.obtain.utils;

import com.lei.obtain.vo.CommentNodeVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author LEI
 * @Date 2021/4/2 16:31
 * @Description TODO
 */
public class CommentBuilder {

    public static List<CommentNodeVO> build(List<CommentNodeVO> nodes) {
        List<CommentNodeVO> rootMenu = new ArrayList<>();
        for (CommentNodeVO node : nodes) {
            if (node.getToId() == 0) {
                rootMenu.add(node);
            }
        }

        for (CommentNodeVO menu : rootMenu) {
            List<CommentNodeVO> childList = getChild(menu.getId(), nodes);
            menu.setReply(childList);
        }
        return rootMenu;
    }

    private static List<CommentNodeVO> getChild(Integer id, List<CommentNodeVO> nodes) {
        List<CommentNodeVO> childList = new ArrayList<>();
        for (CommentNodeVO node : nodes) {
            if (node.getToId().equals(id)) {
                childList.add(node);
            }
        }

        for (CommentNodeVO vo : childList) {
            vo.setReply(getChild(vo.getId(), nodes));
        }

        if (childList.size() == 0) {
            return new ArrayList<>();
        }

        return childList;
    }
}
