package com.lei.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lei.admin.entity.Classification;
import com.lei.admin.entity.FileTag;
import com.lei.admin.entity.Tag;
import com.lei.admin.entity.TinymceTag;
import com.lei.admin.mapper.FileTagMapper;
import com.lei.admin.mapper.TagMapper;
import com.lei.admin.mapper.TinymceTagMapper;
import com.lei.admin.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lei.admin.vo.FileTagVO;
import com.lei.admin.vo.TagVO;
import com.lei.admin.vo.TinymceTagVO;
import com.lei.admin.vo.WordCloudDTO;
import com.lei.error.SystemCodeEnum;
import com.lei.error.SystemException;
import com.lei.utils.PageUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-03-25
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private FileTagMapper fileTagMapper;
    @Autowired
    private TinymceTagMapper tinymceTagMapper;

    @Override
    public PageUtils<Tag> listAll(Integer pageNum, Integer pageSize) {
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        List<Tag> tags = tagMapper.selectList(wrapper);
        PageUtils<Tag> info = new PageUtils<>(pageNum,pageSize);
        info.doPage(tags);
        return info;
    }

    @Override
    public void add(TagVO tagVO) {
        Tag tag = new Tag();
        BeanUtils.copyProperties(tagVO,tag);
        tag.setCreateTime(new Date());
        tagMapper.insert(tag);
    }

    @Override
    public void delete(Integer tagId) {
        tagMapper.deleteById(tagId);
    }

    @Override
    public void edit(Integer id, TagVO tagVO) throws SystemException {
        Tag dbTag = tagMapper.selectById(id);
        if (dbTag == null) {
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"要更新的分类不存在");
        }
        Tag tag = new Tag();
        BeanUtils.copyProperties(tagVO,tag);
        tag.setId(id);
        UpdateWrapper<Tag> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",id);
        tagMapper.update(tag,wrapper);
    }

    @Override
    public void addFileTag(FileTagVO fileTagVO) {
        String identifier = fileTagVO.getIdentifier();
        Integer[] selections = fileTagVO.getSelections();
        QueryWrapper<FileTag> wrapper = new QueryWrapper<>();
        wrapper.eq("identifier",identifier);
        if (fileTagMapper.selectOne(wrapper) == null) {
            FileTag fileTag = new FileTag();
            fileTag.setIdentifier(identifier);
            for (Integer selection : selections) {
                fileTag.setTagId(selection);
                fileTagMapper.insert(fileTag);
            }
        }
    }

    @Override
    public void addArticleTag(TinymceTagVO tinymceTagVO) {
        Integer tinymceId = tinymceTagVO.getTinymceId();
        Integer[] selections = tinymceTagVO.getSelections();
        QueryWrapper<TinymceTag> wrapper = new QueryWrapper<>();
        wrapper.eq("tinymce_id",tinymceId);
        if (tinymceTagMapper.selectOne(wrapper) == null) {
            TinymceTag tinymceTag = new TinymceTag();
            tinymceTag.setTinymceId(tinymceId);
            for (Integer selection : selections) {
               tinymceTag.setTagId(selection);
               tinymceTagMapper.insert(tinymceTag);
            }
        }
    }

    @Override
    public List<Tag> listAll() {
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        return tagMapper.selectList(wrapper);
    }

    @Override
    public List<WordCloudDTO> listWordCloudData() {
        return tagMapper.listWordCloudData();
    }
}
