package com.lei.obtain.service;

import com.lei.admin.vo.ArticleAllVO;
import com.lei.obtain.entity.TinymceScore;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lei.obtain.vo.ScoreVO;
import org.apache.mahout.cf.taste.common.TasteException;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-04-07
 */
public interface ITinymceScoreService extends IService<TinymceScore> {

    void score(ScoreVO scoreVO);

    Integer getScore(Integer tinymceId, String username);

    void addUserScore(ScoreVO scoreVO);

    List<ArticleAllVO> getRecommenderArticle(String username) throws TasteException, ClassNotFoundException;
}
