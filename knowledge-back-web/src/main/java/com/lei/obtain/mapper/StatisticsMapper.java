package com.lei.obtain.mapper;

import com.lei.obtain.vo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
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

    List<StatisticsVO> getStatistics(@Param("username") String username);

    List<TopClickVO> getUserClick(String username);

    List<TopClickVO> getTopClickByTime(@Param("beginDate") Date beginDate, @Param("endDate") Date endDate);
}
