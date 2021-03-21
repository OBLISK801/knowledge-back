package com.lei.obtain.service;

import com.lei.obtain.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lei.obtain.vo.CommentVO;
import com.lei.utils.PageUtils;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-03-19
 */
public interface ICommentService extends IService<Comment> {

    void comment(CommentVO commentVO);

    PageUtils<Comment> getComment(Integer tinymceId, Integer pageNo, Integer pageSize);
}
