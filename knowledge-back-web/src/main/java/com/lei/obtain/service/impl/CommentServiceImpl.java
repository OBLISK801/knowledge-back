package com.lei.obtain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lei.obtain.entity.Comment;
import com.lei.obtain.mapper.CommentMapper;
import com.lei.obtain.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lei.obtain.utils.CommentBuilder;
import com.lei.obtain.utils.CommentConverter;
import com.lei.obtain.vo.CommentNodeVO;
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
 * @since 2021-04-02
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<CommentNodeVO> tree(Integer articleId) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id",articleId);
        List<Comment> results = commentMapper.selectList(wrapper);
        for (Comment result : results) {
            result.setInputShow(false);
        }
        List<CommentNodeVO> commentNodeVOS = CommentConverter.convertToAllCommentNodeVO(results);
        return CommentBuilder.build(commentNodeVOS);
    }

    @Override
    public void add(Comment comment) {
        comment.setTime(new Date());
        commentMapper.insert(comment);
    }
}
