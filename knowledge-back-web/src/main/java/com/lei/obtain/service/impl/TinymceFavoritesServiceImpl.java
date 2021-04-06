package com.lei.obtain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lei.admin.service.ITinymceService;
import com.lei.obtain.vo.FavoriteArticleVO;
import com.lei.obtain.entity.TinymceFavorites;
import com.lei.obtain.mapper.TinymceFavoritesMapper;
import com.lei.obtain.service.ITinymceFavoritesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lei.obtain.vo.TinymceFavoritesVO;
import com.lei.utils.PageUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-04-04
 */
@Service
public class TinymceFavoritesServiceImpl extends ServiceImpl<TinymceFavoritesMapper, TinymceFavorites> implements ITinymceFavoritesService {

    @Autowired
    private TinymceFavoritesMapper tinymceFavoritesMapper;
    @Autowired
    private ITinymceService tinymceService;

    @Override
    public void favoriteArticle(TinymceFavoritesVO favoritesVO) {
        TinymceFavorites favorites = new TinymceFavorites();
        BeanUtils.copyProperties(favoritesVO, favorites);
        favorites.setStatus(1);
        favorites.setTime(new Date());
        favorites.setModifiedTime(new Date());
        tinymceFavoritesMapper.insert(favorites);
    }

    @Override
    public void unFavoriteArticle(TinymceFavoritesVO favoritesVO) {
        QueryWrapper<TinymceFavorites> wrapper = new QueryWrapper<>();
        wrapper.eq("tinymce_id", favoritesVO.getTinymceId()).eq("username", favoritesVO.getUsername()).eq("status", 1);
        TinymceFavorites favorites = tinymceFavoritesMapper.selectOne(wrapper);
        UpdateWrapper<TinymceFavorites> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", favorites.getId());
        favorites.setStatus(0);
        favorites.setModifiedTime(new Date());
        tinymceFavoritesMapper.update(favorites, updateWrapper);
    }

    @Override
    public TinymceFavorites isFavoriteArticle(TinymceFavoritesVO favoritesVO) {
        QueryWrapper<TinymceFavorites> wrapper = new QueryWrapper<>();
        wrapper.eq("tinymce_id", favoritesVO.getTinymceId())
                .eq("username", favoritesVO.getUsername())
                .eq("status", 1);
        return tinymceFavoritesMapper.selectOne(wrapper);
    }

    @Override
    public PageUtils<FavoriteArticleVO> listAll(Integer pageNum, Integer pageSize, String username) {
        List<FavoriteArticleVO> favoriteArticleVOS = tinymceFavoritesMapper.listAll(username);
        for (FavoriteArticleVO vo : favoriteArticleVOS) {
            vo.setTags(tinymceService.listTags(vo.getTinymceId()));
        }
        PageUtils<FavoriteArticleVO> info = new PageUtils<>(pageNum, pageSize);
        info.doPage(favoriteArticleVOS);
        return info;
    }
}
