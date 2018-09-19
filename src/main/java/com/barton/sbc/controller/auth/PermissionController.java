package com.barton.sbc.controller.auth;

import com.barton.sbc.common.ServerResponse;
import com.barton.sbc.domain.entity.auth.AuthPermission;
import com.barton.sbc.domain.entity.auth.AuthUser;
import com.barton.sbc.service.auth.AuthPermissionService;
import com.barton.sbc.utils.CurrentUserUtil;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/auth/permission")
public class PermissionController {
    private static final Logger logger = LoggerFactory.getLogger(PermissionController.class);
    @Autowired
    private AuthPermissionService authPermissionService;
    private static AuthUser currentUser;
    {
        currentUser = CurrentUserUtil.getAuthUser();
    }

    /**
     * 添加
     * @param authPermission
     * @return
     */
    @PostMapping("/insert")
    public ServerResponse insert(AuthPermission authPermission){
        authPermission.setGmtCreate(new Date());
        authPermission.setGmtModified(new Date());
        authPermission.setUserCreate(currentUser.getId());
        authPermission.setUserModified(currentUser.getId());
        if((authPermission = authPermissionService.insert(authPermission)) != null){
            return ServerResponse.createBySuccess(authPermission);
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
        if(authPermissionService.deleteById(id) > 0){
            return ServerResponse.createBySuccessMessage("删除成功");
        }
        return ServerResponse.createByErrorMessage("删除失败");
    }


    /**
     * 修改
     * @param authPermission
     * @return
     */
    @PostMapping("/update")
    public ServerResponse updateById(@RequestBody AuthPermission authPermission){
        if((authPermission = authPermissionService.updateById(authPermission)) != null){
            return ServerResponse.createBySuccessMessage("修改成功");
        }
        return ServerResponse.createByErrorMessage("修改失败");
    }

    /**
     * 查询
     * @param authPermission
     * @return
     */
    @PostMapping("/selectPage")
    public PageInfo<AuthPermission> selectPage(@RequestParam Integer page, @RequestParam Integer pageSize, @RequestBody AuthPermission authPermission){
        return authPermissionService.selectPage(page,pageSize,null);
    }

    /**
     * 查询角色权限
     * @param roleId
     * @return
     */
    @PostMapping("/selectPermissionsByRoleId")
    public List<AuthPermission> selectPermissionsByRoleId(@RequestParam String roleId){
        return authPermissionService.selectByRoleId(roleId);
    }


}
