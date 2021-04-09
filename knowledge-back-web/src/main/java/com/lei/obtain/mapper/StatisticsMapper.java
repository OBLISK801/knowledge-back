package com.lei.obtain.mapper;

import com.lei.obtain.vo.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author LEI
 * @Date 2021/4/8 16:25
 * @Description TODO
 */
@Repository
public interface StatisticsMapper {
    List<AvgRateArticleVO> getTopAvg();

    List<CountLikeVO> getTopLike();

    List<CountFavouriteVO> getTopFavourite();

    List<CountCommentVO> getTopComment();

    List<TopClickVO> getTopClick();

    List<CountPublicVO> getCountPublic();

    List<CountUploadVO> getTopUpload();

    List<CountDownloadVO> getTopDownload();
}
