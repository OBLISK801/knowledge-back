package com.lei.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lei.admin.entity.Tinymce;
import com.lei.admin.mapper.TinymceMapper;
import com.lei.admin.service.ITinymceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lei.admin.utils.PDFUtils;
import com.lei.admin.vo.TinymceVO;
import com.lei.system.entity.User;
import com.lei.utils.PageUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-03-09
 */
@Service
public class TinymceServiceImpl extends ServiceImpl<TinymceMapper, Tinymce> implements ITinymceService {

    @Autowired
    private TinymceMapper tinymceMapper;
    @Value("D:\\GraduationProject\\StageOne\\knowledge-back\\summary")
    private String uploadFolder;

    @Override
    public void saveContent(Tinymce tinymce) {
        QueryWrapper<Tinymce> wrapper = new QueryWrapper<>();
        wrapper.and(i -> i.eq("write_user",tinymce.getWriteUser()).eq("state",0));
        Tinymce oldTinymce = tinymceMapper.selectOne(wrapper);
        int count = tinymceMapper.delete(wrapper);
        if (count > 0) {
            tinymce.setCreateTime(oldTinymce.getCreateTime());
        } else {
            tinymce.setCreateTime(new Date());
        }
        tinymce.setModifiedTime(new Date());
        tinymceMapper.insert(tinymce);
    }

    @Override
    public Tinymce listContent(String writeUser) {
        QueryWrapper<Tinymce> wrapper = new QueryWrapper<>();
        wrapper.and(i -> i.eq("write_user",writeUser).eq("state",0));
        return tinymceMapper.selectOne(wrapper);
    }

    @Override
    public void complete(Tinymce tinymce) {
        tinymce.setCreateTime(new Date());
        tinymce.setModifiedTime(new Date());
        tinymceMapper.insert(tinymce);
        QueryWrapper<Tinymce> wrapper = new QueryWrapper<>();
        wrapper.and(i -> i.eq("write_user",tinymce.getWriteUser()).eq("state",0));
        tinymceMapper.delete(wrapper);
//        String path = uploadFolder + File.separator + tinymce.getWriteUser() + File.separator + tinymce.getFileName() + ".pdf";
//        File dir = new File(uploadFolder + File.separator + tinymce.getWriteUser());
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//        PDFUtils.HtmlToPdf(tinymce.getContent(),path);
    }

    @Override
    public PageUtils<Tinymce> listAll(Integer pageNum, Integer pageSize, TinymceVO tinymceVO) {
        String title = tinymceVO.getTitle();
        Integer classificationId = tinymceVO.getClassificationId();
        String writeUser = tinymceVO.getWriteUser();
        String summary = tinymceVO.getSummary();
        QueryWrapper<Tinymce> wrapper = new QueryWrapper<>();
        List<Tinymce> tinymces = tinymceMapper.selectList(wrapper);
        PageUtils<Tinymce> info = new PageUtils<>(pageNum,pageSize);
        info.doPage(tinymces);
        return info;
    }

    @Override
    public Tinymce listById(Integer tinymceId) {
        QueryWrapper<Tinymce> wrapper = new QueryWrapper<>();
        wrapper.eq("id",tinymceId);
        return tinymceMapper.selectOne(wrapper);
    }

    @Override
    public void edit(Tinymce tinymce) {
        UpdateWrapper<Tinymce> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",tinymce.getId());
        tinymceMapper.update(tinymce,updateWrapper);
    }

    @Override
    public void delete(Integer id) {
        // 删除的文件可能不存在
        tinymceMapper.deleteById(id);
    }
}
