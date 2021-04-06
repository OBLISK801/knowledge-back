package com.lei.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lei.admin.entity.DownloadInfo;
import com.lei.admin.mapper.DownloadInfoMapper;
import com.lei.admin.service.IDownloadInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-04-06
 */
@Service
public class DownloadInfoServiceImpl extends ServiceImpl<DownloadInfoMapper, DownloadInfo> implements IDownloadInfoService {

    @Autowired
    private DownloadInfoMapper downloadInfoMapper;

    @Override
    public void add(DownloadInfo downloadInfo) {
        // 先判断这个用户有没有下载过，没有则新增，有则修改下载次数 --> 要记录每次的下载时间，这样不太适合，除非区分下载与下载明细
//        QueryWrapper<DownloadInfo> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("file_id",downloadInfo.getFileId()).eq("download_user",downloadInfo.getDownloadUser());
//        DownloadInfo oldDownload = downloadInfoMapper.selectOne(queryWrapper);
        downloadInfo.setDownloadTime(new Date());
        downloadInfoMapper.insert(downloadInfo);
    }
}
