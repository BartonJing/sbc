package com.barton.sbc.service.auth;

import com.barton.sbc.domain.entity.auth.AuthRole;
import com.barton.sbc.domain.entity.auth.AuthRolePermission;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface AuthRoleService {
    /**
     * 添加
     * @param authRole
     * @return
     */
    AuthRole insert(AuthRole authRole);

    /**
     * 根据id修改
     * @param authRole
     * @return
     */
    AuthRole updateById(AuthRole authRole);

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
    AuthRole selectById(String id);

    /**
     * 根据传入的参数查询
     * @param params
     * @return
     */
    List<AuthRole> selectByParams(Map<String, Object> params);

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @param params
     * @return
     */
    PageInfo<AuthRole> selectPage(Integer page, Integer pageSize, Map<String, Object> params);

    /**
     * authRolePermission　关联关系
     * @param authRolePermission
     * @return
     */
    int insertRolePermission(AuthRolePermission authRolePermission);

    /**
     * 批量添加role_permission　关联关系
     * @param authRolePermissions
     * @return
     */
    int batchInsertRolePermission(List<AuthRolePermission> authRolePermissions);

    /**
     * 删除RolePermission
     * @param id
     * @return
     */
    int deleteRolePermissionById(String id);

    /**
     * 查询用户角色列表
     * @param userId
     * @return
     */
    List<AuthRole> selectByUserId(String userId);

    /**
     * 删除角色权限关联信息
     * @param roleId
     * @param permissionId
     * @return
     */
    int deleteRolePermissionByRoleIdAndPermission(String roleId,String permissionId);
}
