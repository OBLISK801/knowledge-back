package com.lei.system.mapper;

import com.lei.system.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-02-12
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    List<User>  selectListByCondition(@Param("username") String username,
                                      @Param("nickname") String nickname,
                                      @Param("sex") Integer sex,
                                      @Param("email") String email);
}
