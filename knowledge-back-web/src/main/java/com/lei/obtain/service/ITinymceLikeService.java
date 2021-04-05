package com.lei.obtain.service;

import com.lei.obtain.entity.TinymceLike;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lei.obtain.vo.TinymceLikeVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-04-04
 */
public interface ITinymceLikeService extends IService<TinymceLike> {

    void likeArticle(TinymceLikeVO tinymceLikeVO);

    void unLikeArticle(TinymceLikeVO tinymceLikeVO);

    TinymceLike isLikeArticle(TinymceLikeVO tinymceLikeVO);
}
