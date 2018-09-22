package com.barton.sbc.service.auth;

import com.barton.sbc.domain.entity.auth.AuthPermission;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface AuthPermissionService {
    /**
     * 添加
     * @param authPermission
     * @return
     */
    AuthPermission insert(AuthPermission authPermission);

    /**
     * 根据id修改
     * @param authPermission
     * @return
     */
    AuthPermission updateById(AuthPermission authPermission);

    /**
     * 根据id修改
     * @param id
     * @return
     */
    int deleteById(String id);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    AuthPermission selectById(String id);

    /**
     * 根据传入的参数查询
     * @param params
     * @return
     */
    List<AuthPermission> selectByParams(Map<String, Object> params);

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @param params
     * @return
     */
    PageInfo<AuthPermission> selectPage(Integer page, Integer pageSize, Map<String, Object> params);

    /**
     * 查询全部
     * @return
     */
    List<AuthPermission> findAll();

    /**
     * 查询用户的权限列表
     * @param userId
     * @return
     */
    List<AuthPermission> selectByUserId(String userId);

    /**
     * 查询角色的权限列表
     * @return
     */
    List<AuthPermission> selectByRoleId(String roleId);


}
