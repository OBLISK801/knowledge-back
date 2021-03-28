package com.lei.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lei.admin.entity.Classification;
import com.lei.admin.entity.Tag;
import com.lei.admin.mapper.TagMapper;
import com.lei.admin.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lei.admin.vo.TagVO;
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


}
