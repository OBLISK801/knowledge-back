package com.lei.obtain.mapper;

import com.lei.admin.entity.FileInfo;
import com.lei.admin.entity.Tinymce;
import com.lei.obtain.entity.FieryCount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lei.obtain.vo.TopFileInfoVO;
import com.lei.obtain.vo.TopTinymceVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-03-14
 */
@Repository
public interface FieryCountMapper extends BaseMapper<FieryCount> {

    List<FieryCount> getTopResourceId(@Param("resourceType") Integer resourceId);

    List<TopTinymceVO> getTopTinymce(@Param("resourceIds") List<Integer> resourceIds);

    List<TopFileInfoVO> getTopFileInfo(@Param("resourceIds") List<Integer> resourceIds);
}
