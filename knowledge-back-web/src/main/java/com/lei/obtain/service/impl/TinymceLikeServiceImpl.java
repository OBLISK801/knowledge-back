package com.lei.obtain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lei.obtain.entity.TinymceLike;
import com.lei.obtain.mapper.TinymceLikeMapper;
import com.lei.obtain.service.ITinymceLikeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lei.obtain.vo.TinymceLikeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-04-04
 */
@Service
public class TinymceLikeServiceImpl extends ServiceImpl<TinymceLikeMapper, TinymceLike> implements ITinymceLikeService {

    @Autowired
    private TinymceLikeMapper tinymceLikeMapper;

    @Override
    public void likeArticle(TinymceLikeVO tinymceLikeVO) {
        TinymceLike tinymceLike = new TinymceLike();
        BeanUtils.copyProperties(tinymceLikeVO,tinymceLike);
        tinymceLike.setTime(new Date());
        tinymceLike.setModifiedTime(new Date());
        tinymceLike.setStatus(1);
        tinymceLikeMapper.insert(tinymceLike);
    }

    @Override
    public void unLikeArticle(TinymceLikeVO tinymceLikeVO) {
        QueryWrapper<TinymceLike> wrapper = new QueryWrapper<>();
        wrapper.eq("tinymce_id",tinymceLikeVO.getTinymceId()).eq("username",tinymceLikeVO.getUsername()).eq("status",1);
        TinymceLike tinymceLike = tinymceLikeMapper.selectOne(wrapper);
        UpdateWrapper<TinymceLike> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",tinymceLike.getId());
        tinymceLike.setStatus(0);
        tinymceLike.setModifiedTime(new Date());
        tinymceLikeMapper.update(tinymceLike,updateWrapper);
    }

    @Override
    public TinymceLike isLikeArticle(TinymceLikeVO tinymceLikeVO) {
        QueryWrapper<TinymceLike> wrapper = new QueryWrapper<>();
        wrapper.eq("tinymce_id",tinymceLikeVO.getTinymceId())
                .eq("username",tinymceLikeVO.getUsername())
                .eq("status",1);
        return tinymceLikeMapper.selectOne(wrapper);
    }
}
