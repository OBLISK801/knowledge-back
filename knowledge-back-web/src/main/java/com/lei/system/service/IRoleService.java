package com.lei.system.service;

import com.lei.error.SystemException;
import com.lei.system.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lei.system.vo.RoleVO;
import com.lei.utils.PageUtils;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-02-12
 */
public interface IRoleService extends IService<Role> {

    void authority(Long id,Long[] mids) throws SystemException;

    List<Long> findMenuIdsByRoleId(Long id) throws SystemException;

    PageUtils<RoleVO> findRoleList(Integer pageNum, Integer pageSize, RoleVO roleVO);

    void add(RoleVO roleVO) throws SystemException;

    void deleteById(Long id) throws SystemException;

    RoleVO edit(Long id) throws SystemException;

    void update(Long id, RoleVO roleVO) throws SystemException;

    void updateStatus(Long id, Boolean status) throws SystemException;

    List<Role> findAll();

}
