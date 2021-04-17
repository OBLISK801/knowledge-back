package com.lei.obtain.service;

import com.lei.obtain.vo.FavoriteArticleVO;
import com.lei.obtain.entity.TinymceFavorites;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lei.obtain.vo.TinymceFavoritesVO;
import com.lei.utils.PageUtils;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-04-04
 */
public interface ITinymceFavoritesService extends IService<TinymceFavorites> {

    void favoriteArticle(TinymceFavoritesVO favoritesVO);

    void unFavoriteArticle(TinymceFavoritesVO favoritesVO);

    TinymceFavorites isFavoriteArticle(TinymceFavoritesVO favoritesVO);

    PageUtils<FavoriteArticleVO> listAll(Integer pageNum, Integer pageSize, String username);

    Integer getNum(Integer tinymceId);
}
