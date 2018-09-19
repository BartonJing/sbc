package com.barton.sbc.controller.auth;

import com.barton.sbc.common.ServerResponse;
import com.barton.sbc.domain.entity.auth.AuthRole;
import com.barton.sbc.domain.entity.auth.AuthUser;
import com.barton.sbc.service.auth.AuthRoleService;
import com.barton.sbc.utils.CurrentUserUtil;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/auth/role")
public class RoleController {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
    @Autowired
    private AuthRoleService authRoleService;
    private static AuthUser currentUser;
    {
        currentUser = CurrentUserUtil.getAuthUser();
    }

    /**
     * 添加
     * @param authRole
     * @return
     */
    @PostMapping("/insert")
    public ServerResponse insert(AuthRole authRole){
        if((authRole = authRoleService.insert(authRole)) != null){
            return ServerResponse.createBySuccess(authRole);
        }
        return ServerResponse.createByErrorMessage("添加失败");
    }


    /**
     * 删除
     * @param id
     * @return
     */
    @GetMapping("/delete")
    public ServerResponse deleteById(@RequestParam String id){
        if(authRoleService.deleteById(id) > 0){
            return ServerResponse.createBySuccessMessage("删除成功");
        }
        return ServerResponse.createByErrorMessage("删除失败");
    }


    /**
     * 修改
     * @param authRole
     * @return
     */
    @PostMapping("/update")
    public ServerResponse updateById(@RequestBody AuthRole authRole){
        if((authRole = authRoleService.updateById(authRole)) != null){
            return ServerResponse.createBySuccessMessage("修改成功");
        }
        return ServerResponse.createByErrorMessage("修改失败");
    }

    /**
     * 查询
     * @param authRole
     * @return
     */
    @PostMapping("/selectPage")
    public PageInfo<AuthRole> selectPage(@RequestParam Integer page, @RequestParam Integer pageSize, @RequestBody AuthRole authRole){
        return authRoleService.selectPage(page,pageSize,null);
    }

    /**
     * 查询用户角色
     * @param userId
     * @return
     */
    @PostMapping("/selectRolesByUserId")
    public List<AuthRole> selectRolesByUserId(@RequestParam String userId){
        return authRoleService.selectByUserId(userId);
    }

    /**
     * 删除角色权限关联信息
     * @param permissionId
     * @param roleId
     * @return
     */
    @PostMapping("/deleteRolePermissionByPermissionIdAndRoleId")
    public ServerResponse deleteRolePermissionByPermissionIdAndRoleId(@RequestParam String permissionId,@RequestParam String roleId){
        if(authRoleService.deleteRolePermissionByRoleIdAndPermission(permissionId,roleId) > 0){
            return ServerResponse.createBySuccessMessage("删除成功");
        }
        return ServerResponse.createByErrorMessage("删除失败");
    }

}
