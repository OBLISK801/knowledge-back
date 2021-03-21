package com.lei.obtain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lei.obtain.entity.Comment;
import com.lei.obtain.mapper.CommentMapper;
import com.lei.obtain.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lei.obtain.vo.CommentVO;
import com.lei.utils.PageUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-03-19
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public void comment(CommentVO commentVO) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentVO,comment);
        comment.setCommentTime(new Date());
        commentMapper.insert(comment);
    }

    @Override
    public PageUtils<Comment> getComment(Integer tinymceId, Integer pageNo, Integer pageSize) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("tinymce_id",tinymceId);
        List<Comment> list = commentMapper.selectList(wrapper);
        PageUtils<Comment> info = new PageUtils<>(pageNo,pageSize);
        info.doPage(list);
        return info;
    }
}
