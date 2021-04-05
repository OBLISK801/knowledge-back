package com.lei.obtain.mapper;

import com.lei.obtain.entity.TinymceFavorites;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lei.obtain.vo.FavoriteArticleVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-04-04
 */
@Repository
public interface TinymceFavoritesMapper extends BaseMapper<TinymceFavorites> {
    List<FavoriteArticleVO> listAll();
}
