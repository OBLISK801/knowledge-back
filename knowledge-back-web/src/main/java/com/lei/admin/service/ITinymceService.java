package com.lei.admin.service;

import com.lei.admin.entity.Tinymce;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lei.admin.vo.ArticleAllVO;
import com.lei.admin.vo.ArticleVO;
import com.lei.admin.vo.RecentlyReadVO;
import com.lei.admin.vo.TinymceVO;
import com.lei.utils.PageUtils;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-03-09
 */
public interface ITinymceService extends IService<Tinymce> {
    Integer saveContent(Tinymce tinymce);

    ArticleVO listContent(String writeUser, Integer isArticle);

    Integer complete(Tinymce tinymce);

    PageUtils<ArticleVO> listAll(Integer pageNum, Integer pageSize, Integer isArticle, String username, String searchText, TinymceVO tinymceVO);

    ArticleVO listById(Integer tinymceId);

    void edit(Tinymce tinymce);

    void delete(Integer id);

    PageUtils<ArticleAllVO> getArticleById(Integer pageNum, Integer pageSize, Integer classificationId);

    ArticleAllVO listDetails(Integer tinymceId);

    PageUtils<ArticleAllVO> getArticle(Integer pageNum, Integer pageSize, String username);

    void publicArticle(Integer id);

    List<Integer> listTags(Integer tinymceId);

    List<RecentlyReadVO> getRecentlyRead(String username);

    List<RecentlyReadVO> getTopRead(String name, Integer level);
}
