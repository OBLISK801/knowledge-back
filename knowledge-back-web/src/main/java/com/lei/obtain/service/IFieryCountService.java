package com.lei.obtain.service;

import com.lei.obtain.entity.FieryCount;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-03-14
 */
public interface IFieryCountService extends IService<FieryCount> {
    void addCount(Integer resourceId, Integer type, String userName, Integer resourceType);
}
