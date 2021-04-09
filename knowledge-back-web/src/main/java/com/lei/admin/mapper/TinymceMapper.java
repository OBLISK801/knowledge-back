package com.lei.admin.mapper;

import com.lei.admin.entity.Tinymce;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lei.admin.vo.ArticleAllVO;
import com.lei.admin.vo.ArticleVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-03-09
 */
@Repository
public interface TinymceMapper extends BaseMapper<Tinymce> {
    List<ArticleAllVO> getArticleByClassificationId(@Param("classificationId") Integer classificationId);

    ArticleAllVO getArticleById(@Param("tinymceId") Integer tinymceId);

    List<ArticleAllVO> getArticle(@Param("username") String username);

    List<ArticleAllVO> getArticleByIds(@Param("ids") int[] ids);
}
