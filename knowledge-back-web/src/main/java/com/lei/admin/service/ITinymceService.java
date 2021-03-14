package com.lei.admin.service;

import com.lei.admin.entity.Tinymce;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lei.admin.vo.TinymceVO;
import com.lei.utils.PageUtils;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-03-09
 */
public interface ITinymceService extends IService<Tinymce> {
    void saveContent(Tinymce tinymce);
    Tinymce listContent(String writeUser);
    void complete(Tinymce tinymce);
    PageUtils<Tinymce> listAll(Integer pageNum, Integer pageSize, TinymceVO tinymceVO);
    Tinymce listById(Integer tinymceId);
    void edit(Tinymce tinymce);
    void delete(Integer id);
}
