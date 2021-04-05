package com.lei.obtain.utils;

import com.lei.obtain.entity.Comment;
import com.lei.obtain.vo.CommentNodeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author LEI
 * @Date 2021/4/2 16:31
 * @Description TODO
 */
public class CommentConverter {
    public static List<CommentNodeVO> convertToAllCommentNodeVO(List<Comment> comments) {
        List<CommentNodeVO> commentNodeVOS = new ArrayList<>();
        if ((!CollectionUtils.isEmpty(comments))) {
            for (Comment comment : comments) {
                CommentNodeVO commentNodeVO = new CommentNodeVO();
                BeanUtils.copyProperties(comment,commentNodeVO);
                commentNodeVOS.add(commentNodeVO);
            }
        }
        return commentNodeVOS;
    }
}
