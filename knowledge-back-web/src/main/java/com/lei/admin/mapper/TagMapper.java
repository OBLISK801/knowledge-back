package com.lei.admin.mapper;

import com.lei.admin.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lei.admin.vo.WordCloudDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-03-25
 */
@Repository
public interface TagMapper extends BaseMapper<Tag> {
    List<WordCloudDTO> listWordCloudData();

    List<String> getTagName(@Param("tinymceId") Integer tinymceId);

    List<Tag> getTopTag();
}
