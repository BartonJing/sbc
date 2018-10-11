package com.barton.sbc.controller.auth;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.barton.sbc.annotation.SysLog;
import com.barton.sbc.common.ServerResponse;
import com.barton.sbc.dao.auth.AuthRolePermissionMapper;
import com.barton.sbc.domain.entity.auth.AuthPermission;
import com.barton.sbc.domain.entity.auth.AuthRole;
import com.barton.sbc.domain.entity.auth.AuthRolePermission;
import com.barton.sbc.domain.entity.auth.AuthUser;
import com.barton.sbc.service.auth.AuthRoleService;
import com.barton.sbc.utils.CurrentUserUtil;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/auth/role")
public class RoleController {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
    @Autowired
    private AuthRoleService authRoleService;
    @Autowired
    private AuthRolePermissionMapper authRolePermissionMapper;

    private static AuthUser currentUser;


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        currentUser = CurrentUserUtil.getAuthUser();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
    }

    /**
     * 添加/修改
     *
     * @param authRole
     * @return
     */
    @PostMapping("/save")
    @SysLog(value = "保存角色信息", type = SysLog.SysLogType.SAVE)
    public ServerResponse save(@Validated @RequestBody AuthRole authRole) {
        if (authRole == null) {
            return ServerResponse.createByErrorMessage("保存失败！");
        }
        //添加
        if (StrUtil.isEmpty(authRole.getId())) {
            authRole.setId(RandomUtil.randomUUID());
            authRole.setGmtCreate(new Date());
            authRole.setGmtModified(new Date());
            authRole.setUserCreate(currentUser.getId());
            authRole.setUserModified(currentUser.getId());
            if ((authRoleService.insert(authRole)) != null) {
                return ServerResponse.createBySuccessMessage("添加成功！");
            }
        } else {//修改
            authRole.setGmtModified(new Date());
            authRole.setUserModified(currentUser.getId());
            if ((authRoleService.updateById(authRole)) != null) {
                return ServerResponse.createBySuccessMessage("修改成功");
            }
        }
        return ServerResponse.createByErrorMessage("保存失败");
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @GetMapping("/delete")
    @Transactional
    public ServerResponse deleteById(@RequestParam String id) {
        if (authRoleService.deleteById(id) > 0) {
            return ServerResponse.createBySuccessMessage("删除成功");
        }
        return ServerResponse.createByErrorMessage("删除失败");
    }

    /**
     * 查询 所有的权限
     *
     * @return
     */
    @GetMapping("/selectAllRoles")
    public List<AuthRole> selectAllRoles() {
        return authRoleService.selectByParams(null);
    }
    /**
     * 查询
     *
     * @param id
     * @return
     */
    @GetMapping("/selectById")
    public AuthRole selectById(@RequestParam String id) {
        return authRoleService.selectById(id);
    }
    /**
     * 查询
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/selectPage")
    public PageInfo<AuthRole> selectPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return authRoleService.selectPage(pageNum, pageSize, null);
    }


    /**
     * 查询用户角色
     *
     * @param userId
     * @return
     */
    @GetMapping("/selectRolesByUserId")
    public List<AuthRole> selectRolesByUserId(@RequestParam String userId) {
        return authRoleService.selectByUserId(userId);
    }

    /**
     * 删除角色权限关联信息
     *
     * @param permissionId
     * @param roleId
     * @return
     */
    @GetMapping("/deleteRolePermissionByPermissionIdAndRoleId")
    public ServerResponse deleteRolePermissionByPermissionIdAndRoleId(@RequestParam String permissionId, @RequestParam String roleId) {
        if (authRoleService.deleteRolePermissionByRoleIdAndPermission(permissionId, roleId) > 0) {
            return ServerResponse.createBySuccessMessage("删除成功");
        }
        return ServerResponse.createByErrorMessage("删除失败");
    }

    /**
     * 添加角色相关的权限信息
     *
     * @param permissions
     * @param roleId
     * @return
     */
    @PostMapping("/saveRolePermissions")
    @Transactional
    public ServerResponse saveRolePermissions(@RequestParam(value = "permissions") String[] permissions, @RequestParam String roleId) {
        if (StrUtil.isEmpty(roleId)) {
            ServerResponse.createByErrorMessage("数据错误，请联系管理员");
        }
        authRolePermissionMapper.deleteByRoleId(roleId);
        List<AuthRolePermission> authPermissions = new ArrayList<AuthRolePermission>();
        if (permissions != null && permissions.length > 0) {
            for (String permissionId : permissions) {
                if("0".equals(permissionId)){
                    continue;
                }
                AuthRolePermission arp = new AuthRolePermission();
                arp.setId(RandomUtil.randomUUID());
                arp.setRoleId(roleId);
                arp.setPermissionId(permissionId);
                arp.setGmtCreate(new Date());
                arp.setGmtModified(new Date());
                authPermissions.add(arp);
            }
        }
        if(authPermissions.size() > 0){
            authRoleService.batchInsertRolePermission(authPermissions);
        }
        return ServerResponse.createBySuccessMessage("保存成功！");
    }

}
