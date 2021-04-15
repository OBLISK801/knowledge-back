package com.lei.obtain.controller;

import com.lei.obtain.service.IStatisticsService;
import com.lei.obtain.vo.*;
import com.lei.response.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author LEI
 * @Date 2021/4/8 15:38
 * @Description TODO
 */
@RestController
@RequestMapping("/obtain/statistics")
public class StatisticsController {

    @Autowired
    private IStatisticsService statisticsService;

    @GetMapping("/getTopAvg")
    public ResponseModel<List<AvgRateArticleVO>> getTopAvg() {
        List<AvgRateArticleVO> result = statisticsService.getTopAvg();
        return ResponseModel.success(result);
    }

    @GetMapping("/getTopLike")
    public ResponseModel<List<CountLikeVO>> getTopLike() {
        List<CountLikeVO> result = statisticsService.getTopLike();
        return ResponseModel.success(result);
    }

    @GetMapping("/getTopFavourite")
    public ResponseModel<List<CountFavouriteVO>> getTopFavourite() {
        List<CountFavouriteVO> result = statisticsService.getTopFavourite();
        return ResponseModel.success(result);
    }

    @GetMapping("/getTopComment")
    public ResponseModel<List<CountCommentVO>> getTopComment() {
        List<CountCommentVO> result = statisticsService.getTopComment();
        return ResponseModel.success(result);
    }

    @GetMapping("/getTopClick")
    public ResponseModel<List<TopClickVO>> getTopClick() {
        List<TopClickVO> result = statisticsService.getTopClick();
        return ResponseModel.success(result);
    }

    @GetMapping("/getTopClickByTime")
    public ResponseModel<List<TopClickVO>> getTopClickByTime(TimeVO timeVO) {
        List<TopClickVO> result = statisticsService.getTopClickByTime(timeVO);
        return ResponseModel.success(result);
    }

    @GetMapping("/getCountPublic")
    public ResponseModel<List<CountPublicVO>> getCountPublic() {
        List<CountPublicVO> result = statisticsService.getCountPublic();
        return ResponseModel.success(result);
    }

    @GetMapping("/getTopUpload")
    public ResponseModel<List<CountUploadVO>> getTopUpload() {
        List<CountUploadVO> result = statisticsService.getTopUpload();
        return ResponseModel.success(result);
    }

    @GetMapping("/getTopDownload")
    public ResponseModel<List<CountDownloadVO>> getTopDownload() {
        List<CountDownloadVO> result = statisticsService.getTopDownload();
        return ResponseModel.success(result);
    }

    @GetMapping("/getStatistics")
    public ResponseModel<List<StatisticsVO>> getStatistics(@RequestParam("username")String username) {
        List<StatisticsVO> result = statisticsService.getStatistics(username);
        return ResponseModel.success(result);
    }

    @GetMapping("/getUserClick")
    public ResponseModel<List<TopClickVO>> getUserClick(@RequestParam("username")String username) {
        List<TopClickVO> result = statisticsService.getUserClick(username);
        return ResponseModel.success(result);
    }



}
