package com.barton.sbc.controller.auth;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.barton.sbc.annotation.SysLog;
import com.barton.sbc.common.ServerResponse;
import com.barton.sbc.domain.entity.auth.AuthPermission;
import com.barton.sbc.domain.entity.auth.AuthUser;
import com.barton.sbc.service.auth.AuthPermissionService;
import com.barton.sbc.utils.CurrentUserUtil;
import com.barton.sbc.utils.TreeUtil;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(value = "/auth/permission")
public class PermissionController {
    private static final Logger logger = LoggerFactory.getLogger(PermissionController.class);
    @Autowired
    private AuthPermissionService authPermissionService;
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
     * @param authPermission
     * @return
     */
    @PostMapping("/save")
    @SysLog(value = "保存菜单信息",type = SysLog.SysLogType.SAVE)
    public ServerResponse save(@Validated @RequestBody AuthPermission authPermission){
        if(authPermission == null){
            return ServerResponse.createByErrorMessage("保存失败！");
        }
        //添加
        if(StrUtil.isEmpty(authPermission.getId())){
            authPermission.setId(RandomUtil.randomUUID());
            authPermission.setGmtCreate(new Date());
            authPermission.setGmtModified(new Date());
            authPermission.setUserCreate(currentUser.getId());
            authPermission.setUserModified(currentUser.getId());
            if((authPermissionService.insert(authPermission)) != null){
                return ServerResponse.createBySuccessMessage("添加成功！");
            }
        }else{//修改
            authPermission.setGmtModified(new Date());
            authPermission.setUserModified(currentUser.getId());
            if((authPermissionService.updateById(authPermission)) != null){
                return ServerResponse.createBySuccessMessage("修改成功");
            }
        }
        return ServerResponse.createByErrorMessage("保存失败");
    }
    /**
     * 查询
     * @param id
     * @return
     */
    @GetMapping("/selectById")
    public AuthPermission selectById(@RequestParam String id){
        return authPermissionService.selectById(id);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @GetMapping("/delete")
    @SysLog(value = "删除菜单信息",type = SysLog.SysLogType.DELETE)
    public ServerResponse deleteById(@RequestParam String id){
        if(authPermissionService.deleteById(id) > 0){
            return ServerResponse.createBySuccessMessage("删除成功");
        }
        return ServerResponse.createByErrorMessage("删除失败");
    }


    /**
     * 查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/selectPage")
    public PageInfo<AuthPermission> selectPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize){
        return authPermissionService.selectPage(pageNum,pageSize,null);
    }

    /**
     * 查询角色权限
     * @param roleId
     * @return
     */
    @GetMapping("/selectPermissionsByRoleId")
    public List<AuthPermission> selectPermissionsByRoleId(@RequestParam String roleId){
        return authPermissionService.selectByRoleId(roleId);
    }

    /**
     * 查询所有权限（树型菜单）
     * @param hasRoot 1：带根节点 其他：不带根几点
     * @return
     */
    @GetMapping("/selectAllPermissionToTree")
    public List<AuthPermission> selectAllPermissionToTree(Integer hasRoot){
        List<AuthPermission> aps = authPermissionService.findAll();
        AuthPermission root = new AuthPermission("0",null,"菜单");
        root = TreeUtil.getTree(root,aps);
        if(hasRoot == 1){
            aps = new ArrayList<AuthPermission>();
            aps.add(root);
            return aps;
        }else{
            return root.getChildNodes();
        }

    }

    /**
     * 查询用户权限信息
     * @param userId
     * @return
     */
    @GetMapping("/selectUserPermissions")
    public List<AuthPermission> selectUserPermissions(@RequestParam String userId){
        List<AuthPermission> authPermissions = authPermissionService.selectByUserId(userId);
        return authPermissions;
    }

    /**
     * 查询用户权限信息树结构
     * @param userId
     * @return
     */
    @GetMapping("/selectUserPermissionsTree")
    public List<AuthPermission> selectUserPermissionsTree(@RequestParam String userId){
        List<AuthPermission> authPermissions = authPermissionService.selectByUserId(userId);
        AuthPermission root = new AuthPermission("0",null,"菜单");
        root = TreeUtil.getTree(root,authPermissions);
        return root.getChildNodes();
    }




}
