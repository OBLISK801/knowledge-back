package com.lei.obtain.service;

import com.lei.admin.entity.FileInfo;
import com.lei.admin.entity.Tinymce;
import com.lei.obtain.entity.FieryCount;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lei.obtain.vo.TopFileInfoVO;
import com.lei.obtain.vo.TopTinymceVO;

import java.util.List;

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

    List<TopTinymceVO> getTopTinymce();

    List<TopFileInfoVO> getTopFileInfo();
}
