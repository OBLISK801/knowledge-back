package com.lei.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lei.admin.entity.Tinymce;
import com.lei.admin.entity.TinymceTag;
import com.lei.admin.mapper.TagMapper;
import com.lei.admin.mapper.TinymceMapper;
import com.lei.admin.mapper.TinymceTagMapper;
import com.lei.admin.service.ITinymceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lei.admin.utils.PDFUtils;
import com.lei.admin.vo.ArticleAllVO;
import com.lei.admin.vo.ArticleVO;
import com.lei.admin.vo.RecentlyReadVO;
import com.lei.admin.vo.TinymceVO;
import com.lei.obtain.entity.FieryCount;
import com.lei.obtain.mapper.FieryCountMapper;
import com.lei.system.entity.User;
import com.lei.utils.PageUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-03-09
 */
@Service
public class TinymceServiceImpl extends ServiceImpl<TinymceMapper, Tinymce> implements ITinymceService {

    @Autowired
    private TinymceMapper tinymceMapper;
    @Autowired
    private TinymceTagMapper tinymceTagMapper;
    @Autowired
    private FieryCountMapper fieryCountMapper;
    @Autowired
    private TagMapper tagMapper;
    @Value("D:\\GraduationProject\\StageOne\\knowledge-back\\summary")
    private String uploadFolder;

    @Override
    public Integer saveContent(Tinymce tinymce) {
        QueryWrapper<Tinymce> wrapper = new QueryWrapper<>();
        wrapper.and(i -> i.eq("write_user", tinymce.getWriteUser()).eq("state", 0));
        Tinymce oldTinymce = tinymceMapper.selectOne(wrapper);
        int count = tinymceMapper.delete(wrapper);
        if (count > 0) {
            System.out.println(oldTinymce.getId());
            this.deleteTags(oldTinymce.getId());
            tinymce.setCreateTime(oldTinymce.getCreateTime());
        } else {
            tinymce.setCreateTime(new Date());
        }
        tinymce.setModifiedTime(new Date());
        tinymceMapper.insert(tinymce);
        return tinymce.getId();
    }

    @Override
    public ArticleVO listContent(String writeUser, Integer isArticle) {
        QueryWrapper<Tinymce> wrapper = new QueryWrapper<>();
        wrapper.and(i -> i.eq("write_user", writeUser).eq("state", 0).eq("is_article", isArticle));
        Tinymce tinymce = tinymceMapper.selectOne(wrapper);
        ArticleVO articleVO = new ArticleVO();
        if (tinymce != null) {
            Integer id = tinymce.getId();
            BeanUtils.copyProperties(tinymce, articleVO);
            articleVO.setTags(this.listTags(id));
        }
        return articleVO;
    }

    @Override
    public Integer complete(Tinymce tinymce) {
        tinymce.setCreateTime(new Date());
        tinymce.setModifiedTime(new Date());
        tinymceMapper.insert(tinymce);
        QueryWrapper<Tinymce> wrapper = new QueryWrapper<>();
        wrapper.and(i -> i.eq("write_user", tinymce.getWriteUser()).eq("state", 0).eq("is_article", tinymce.getIsArticle()));
        Tinymce draft = tinymceMapper.selectOne(wrapper);
        if (draft != null) {
            this.deleteTags(draft.getId());
        }
        tinymceMapper.delete(wrapper);
//        String path = uploadFolder + File.separator + tinymce.getWriteUser() + File.separator + tinymce.getFileName() + ".pdf";
//        File dir = new File(uploadFolder + File.separator + tinymce.getWriteUser());
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//        PDFUtils.HtmlToPdf(tinymce.getContent(),path);
        return tinymce.getId();
    }

    @Override
    public PageUtils<ArticleVO> listAll(Integer pageNum, Integer pageSize, Integer isArticle, String username,String searchText, TinymceVO tinymceVO) {
        String title = tinymceVO.getTitle();
        Integer classificationId = tinymceVO.getClassificationId();
        String writeUser = tinymceVO.getWriteUser();
        String summary = tinymceVO.getSummary();
        QueryWrapper<Tinymce> wrapper = new QueryWrapper<>();
        wrapper.eq("is_article", isArticle).eq("write_user", username).like("title",searchText);
        List<Tinymce> tinymce = tinymceMapper.selectList(wrapper);
        List<ArticleVO> articleVOS = new ArrayList<>();
        // 这样只是浅拷贝对于List 和 Map来说是不会有任何作用  BeanUtils.copyProperties(tinymce,articleVOS);
        // 拷贝数组
        for (Tinymce tinymce1 : tinymce) {
            ArticleVO articleVO = new ArticleVO();
            BeanUtils.copyProperties(tinymce1, articleVO);
            articleVO.setTags(this.listTags(tinymce1.getId()));
            articleVO.setTagName(tagMapper.getTagName(tinymce1.getId()));
            articleVOS.add(articleVO);
        }
        PageUtils<ArticleVO> info = new PageUtils<>(pageNum, pageSize);
        info.doPage(articleVOS);
        return info;
    }

    @Override
    public ArticleVO listById(Integer tinymceId) {
        QueryWrapper<Tinymce> wrapper = new QueryWrapper<>();
        wrapper.eq("id", tinymceId);
        Tinymce tinymce = tinymceMapper.selectOne(wrapper);
        ArticleVO articleVO = new ArticleVO();
        BeanUtils.copyProperties(tinymce, articleVO);
        articleVO.setTags(this.listTags(tinymceId));
        return articleVO;
    }

    @Override
    public void edit(Tinymce tinymce) {
        UpdateWrapper<Tinymce> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", tinymce.getId());
        tinymceMapper.update(tinymce, updateWrapper);
        this.deleteTags(tinymce.getId());
    }

    @Override
    public void delete(Integer id) {
        // 删除的文件可能不存在
        tinymceMapper.deleteById(id);
        QueryWrapper<FieryCount> wrapper = new QueryWrapper<>();
        wrapper.eq("resource_id", id);
        fieryCountMapper.delete(wrapper);
        this.deleteTags(id);
    }

    public void deleteTags(Integer tinymceId) {
        QueryWrapper<TinymceTag> wrapper = new QueryWrapper<>();
        wrapper.eq("tinymce_id", tinymceId);
        tinymceTagMapper.delete(wrapper);
    }

    @Override
    public List<Integer> listTags(Integer tinymceId) {
        QueryWrapper<TinymceTag> wrapper = new QueryWrapper<>();
        wrapper.eq("tinymce_id", tinymceId);
        List<TinymceTag> tinymceTags = tinymceTagMapper.selectList(wrapper);
        List<Integer> result = new ArrayList<>();
        for (TinymceTag tinymceTag : tinymceTags) {
            result.add(tinymceTag.getTagId());
        }
        return result;
    }

    @Override
    public PageUtils<ArticleAllVO> getArticleById(Integer pageNum, Integer pageSize, Integer classificationId) {
        List<ArticleAllVO> articleAllVOS = tinymceMapper.getArticleByClassificationId(classificationId);
        for (ArticleAllVO vo : articleAllVOS) {
            List<String> strings = tagMapper.getTagName(vo.getId());
            vo.setTagName(strings);
        }
        PageUtils<ArticleAllVO> info = new PageUtils<>(pageNum, pageSize);
        info.doPage(articleAllVOS);
        return info;
    }

    @Override
    public ArticleAllVO listDetails(Integer tinymceId) {
        ArticleAllVO articleAllVO = tinymceMapper.getArticleById(tinymceId);
        List<String> strings = tagMapper.getTagName(articleAllVO.getId());
        articleAllVO.setTagName(strings);
        return articleAllVO;
    }

    @Override
    public PageUtils<ArticleAllVO> getArticle(Integer pageNum, Integer pageSize, String username) {
        List<ArticleAllVO> articleAllVOS = tinymceMapper.getArticle(username);
        for (ArticleAllVO vo : articleAllVOS) {
            List<String> strings = tagMapper.getTagName(vo.getId());
            vo.setTagName(strings);
        }
        PageUtils<ArticleAllVO> info = new PageUtils<>(pageNum, pageSize);
        info.doPage(articleAllVOS);
        return info;
    }

    @Override
    public void publicArticle(Integer id) {
        Tinymce tinymce = tinymceMapper.selectById(id);
        tinymce.setIsPublic(1);
        UpdateWrapper<Tinymce> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",tinymce.getId());
        tinymceMapper.update(tinymce,wrapper);
    }

    @Override
    public List<RecentlyReadVO> getRecentlyRead(String username) {
        List<RecentlyReadVO> recentlyReadVOS = tinymceMapper.getRecentlyRead(username);
        for (RecentlyReadVO vo : recentlyReadVOS) {
            List<String> strings = tagMapper.getTagName(vo.getResourceId());
            vo.setTagName(strings);
        }
        return recentlyReadVOS;
    }
}
