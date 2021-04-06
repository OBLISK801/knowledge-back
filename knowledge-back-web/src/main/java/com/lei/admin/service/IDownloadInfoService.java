package com.lei.admin.service;

import com.lei.admin.entity.DownloadInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-04-06
 */
public interface IDownloadInfoService extends IService<DownloadInfo> {

    void add(DownloadInfo downloadInfo);
}
