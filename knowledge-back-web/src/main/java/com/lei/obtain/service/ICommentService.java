package com.lei.obtain.service;

import com.lei.obtain.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lei.obtain.vo.CommentNodeVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-04-02
 */
public interface ICommentService extends IService<Comment> {

    List<CommentNodeVO> tree(Integer articleId);

    void add(Comment comment);
}
