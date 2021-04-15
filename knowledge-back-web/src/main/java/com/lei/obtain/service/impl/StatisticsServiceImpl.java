package com.lei.obtain.service.impl;

import com.lei.obtain.mapper.StatisticsMapper;
import com.lei.obtain.service.IStatisticsService;
import com.lei.obtain.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author LEI
 * @Date 2021/4/8 15:39
 * @Description TODO
 */
@Service
public class StatisticsServiceImpl implements IStatisticsService {

    @Autowired
    private StatisticsMapper statisticsMapper;

    @Override
    public List<AvgRateArticleVO> getTopAvg() {
        return statisticsMapper.getTopAvg();
    }

    @Override
    public List<CountLikeVO> getTopLike() {
        return statisticsMapper.getTopLike();
    }

    @Override
    public List<CountFavouriteVO> getTopFavourite() {
        return statisticsMapper.getTopFavourite();
    }

    @Override
    public List<CountCommentVO> getTopComment() {
        return statisticsMapper.getTopComment();
    }

    @Override
    public List<TopClickVO> getTopClick() {
        return statisticsMapper.getTopClick();
    }

    @Override
    public List<CountPublicVO> getCountPublic() {
        return statisticsMapper.getCountPublic();
    }

    @Override
    public List<CountUploadVO> getTopUpload() {
        return statisticsMapper.getTopUpload();
    }

    @Override
    public List<CountDownloadVO> getTopDownload() {
        return statisticsMapper.getTopDownload();
    }

    @Override
    public List<StatisticsVO> getStatistics(String username) {
        return statisticsMapper.getStatistics(username);
    }

    @Override
    public List<TopClickVO> getUserClick(String username) {
        return statisticsMapper.getUserClick(username);
    }

    @Override
    public List<TopClickVO> getTopClickByTime(TimeVO timeVO) {
        return statisticsMapper.getTopClickByTime(timeVO.getBeginDate(),timeVO.getEndDate());
    }
}
