package com.lei.obtain.service;

import com.lei.obtain.vo.*;

import java.util.List;

/**
 * @Author LEI
 * @Date 2021/4/8 15:39
 * @Description TODO
 */
public interface IStatisticsService {
    List<AvgRateArticleVO> getTopAvg();

    List<CountLikeVO> getTopLike();

    List<CountFavouriteVO> getTopFavourite();

    List<CountCommentVO> getTopComment();

    List<TopClickVO> getTopClick();

    List<CountPublicVO> getCountPublic();

    List<CountUploadVO> getTopUpload();

    List<CountDownloadVO> getTopDownload();
}
