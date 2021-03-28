package com.lei.admin.service;

import com.lei.admin.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lei.admin.vo.TagVO;
import com.lei.error.SystemException;
import com.lei.utils.PageUtils;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-03-25
 */
public interface ITagService extends IService<Tag> {

    PageUtils<Tag> listAll(Integer pageNum, Integer pageSize);

    void add(TagVO tagVO);

    void delete(Integer tagId);

    void edit(Integer id, TagVO tagVO) throws SystemException;
}
