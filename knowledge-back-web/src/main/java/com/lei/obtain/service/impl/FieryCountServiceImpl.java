package com.lei.obtain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lei.admin.entity.FileInfo;
import com.lei.admin.entity.Tinymce;
import com.lei.admin.service.IFileInfoService;
import com.lei.obtain.entity.FieryCount;
import com.lei.obtain.mapper.FieryCountMapper;
import com.lei.obtain.service.IFieryCountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lei.obtain.vo.TopFileInfoVO;
import com.lei.obtain.vo.TopTinymceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-03-14
 */
@Service
public class FieryCountServiceImpl extends ServiceImpl<FieryCountMapper, FieryCount> implements IFieryCountService {

    @Autowired
    private FieryCountMapper fieryCountMapper;


    /**
     * 为了解耦合 可以使用enum类代替数字
     *
     * @param resourceId
     * @param type         1-download(文件) 2-preview(文件、文章) 所有其他行为都记录点击
     * @param userName
     * @param resourceType 1-文件 2-文章
     */
    @Override
    public void addCount(Integer resourceId, Integer type, String userName, Integer resourceType) {
        // 首先判断数据库中有没有该资源id的记录 无 插入 有 修改对应字段
        QueryWrapper<FieryCount> wrapper = new QueryWrapper<>();
        wrapper.eq("resource_id", resourceId);
        int num = fieryCountMapper.selectCount(wrapper);
        if (num == 0) {
            FieryCount fieryCount = new FieryCount();
            fieryCount.setHappenTime(new Date());
            fieryCount.setResourceId(resourceId);
            fieryCount.setUserName(userName);
            // 为修改做准备
            fieryCount.setClickCount(0);
            fieryCount.setDownloadCount(0);
            fieryCount.setPreviewCount(0);
            fieryCount.setResourceType(resourceType);
            // 插入
            if (type == 1) {
                fieryCount.setClickCount(1);
                fieryCount.setDownloadCount(1);
            } else {
                fieryCount.setClickCount(1);
                fieryCount.setPreviewCount(1);
            }
            fieryCountMapper.insert(fieryCount);
        } else {
            FieryCount dbfiery = fieryCountMapper.selectOne(wrapper);
            if (type == 1) {
                dbfiery.setClickCount(dbfiery.getClickCount() + 1);
                dbfiery.setDownloadCount(dbfiery.getDownloadCount() + 1);
            } else {
                dbfiery.setClickCount(dbfiery.getClickCount() + 1);
                dbfiery.setPreviewCount(dbfiery.getPreviewCount() + 1);
            }
            UpdateWrapper<FieryCount> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", dbfiery.getId());
            fieryCountMapper.update(dbfiery, updateWrapper);
        }
    }

    public List<Integer> getTopResourceId(Integer resourceType) {
        List<FieryCount> topResourceId = fieryCountMapper.getTopResourceId(resourceType);
        List<Integer> top = new ArrayList<>();
        for (FieryCount fieryCount : topResourceId) {
            top.add(fieryCount.getResourceId());
        }
        return top;
    }



    @Override
    public List<TopTinymceVO> getTopTinymce() {
        List<Integer> top = this.getTopResourceId(2);
        return fieryCountMapper.getTopTinymce(top);
    }

//    @Override
//    public List<TopFileInfoVO> getTopFileInfo() {
//        List<Integer> top = this.getTopResourceId(1);
//        return fieryCountMapper.getTopFileInfo(top);
//    }
}
