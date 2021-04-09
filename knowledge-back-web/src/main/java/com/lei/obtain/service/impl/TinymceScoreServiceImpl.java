package com.lei.obtain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lei.admin.mapper.TagMapper;
import com.lei.admin.mapper.TinymceMapper;
import com.lei.admin.vo.ArticleAllVO;
import com.lei.obtain.entity.TinymceScore;
import com.lei.obtain.entity.UserScore;
import com.lei.obtain.mapper.TinymceScoreMapper;
import com.lei.obtain.mapper.UserScoreMapper;
import com.lei.obtain.service.ITinymceScoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lei.obtain.utils.RecommenderUtils;
import com.lei.obtain.vo.ScoreVO;
import com.lei.system.entity.User;
import com.lei.system.mapper.UserMapper;
import org.apache.mahout.cf.taste.common.TasteException;
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
 * @since 2021-04-07
 */
@Service
public class TinymceScoreServiceImpl extends ServiceImpl<TinymceScoreMapper, TinymceScore> implements ITinymceScoreService {

    @Autowired
    private TinymceScoreMapper tinymceScoreMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserScoreMapper userScoreMapper;
    @Autowired
    private TinymceMapper tinymceMapper;
    @Autowired
    private TagMapper tagMapper;

    @Override
    public void score(ScoreVO scoreVO) {
        QueryWrapper<TinymceScore> wrapper = new QueryWrapper<>();
        wrapper.eq("tinymce_id",scoreVO.getTinymceId()).eq("username",scoreVO.getUsername());
        TinymceScore tinymceScore = tinymceScoreMapper.selectOne(wrapper);
        if (tinymceScore != null) {
            tinymceScore.setScore(scoreVO.getScore());
            tinymceScore.setTime(new Date());
            UpdateWrapper<TinymceScore> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id",tinymceScore.getId());
            tinymceScoreMapper.update(tinymceScore,updateWrapper);
        } else {
            TinymceScore score = new TinymceScore();
            BeanUtils.copyProperties(scoreVO,score);
            score.setTime(new Date());
            tinymceScoreMapper.insert(score);
        }
    }

    @Override
    public Integer getScore(Integer tinymceId, String username) {
        QueryWrapper<TinymceScore> wrapper = new QueryWrapper<>();
        wrapper.eq("tinymce_id",tinymceId).eq("username",username);
        TinymceScore tinymceScore = tinymceScoreMapper.selectOne(wrapper);
        if (tinymceScore != null) {
            return tinymceScore.getScore();
        }
        return 0;
    }

    @Override
    public void addUserScore(ScoreVO scoreVO) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",scoreVO.getUsername());
        User user = userMapper.selectOne(userQueryWrapper);
        UserScore userScore = new UserScore();
        userScore.setUserId(user.getId());
        userScore.setTinymceId(scoreVO.getTinymceId());
        userScore.setScore(scoreVO.getScore());
        userScore.setTime(new Date());
        userScoreMapper.insert(userScore);
    }

    @Override
    public List<ArticleAllVO> getRecommenderArticle(String username) throws TasteException, ClassNotFoundException {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",username);
        User user = userMapper.selectOne(userQueryWrapper);
        int[] ids = RecommenderUtils.recommendByUser(user.getId());
        List<ArticleAllVO> articleAllVOS = tinymceMapper.getArticleByIds(ids);
        for (ArticleAllVO vo : articleAllVOS) {
            List<String> strings = tagMapper.getTagName(vo.getId());
            vo.setTagName(strings);
        }
        return articleAllVOS;
    }
}
