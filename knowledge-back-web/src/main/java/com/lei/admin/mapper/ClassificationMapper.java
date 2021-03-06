package com.lei.admin.mapper;

import com.lei.admin.entity.Classification;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-02-25
 */
@Repository
public interface ClassificationMapper extends BaseMapper<Classification> {

    Integer getNumOne();

    Integer getNumTwo(@Param("id") Integer id);

    Integer getNumThree(@Param("id") Integer id);
}
