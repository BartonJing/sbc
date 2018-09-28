package com.barton.sbc.controller.auth;

import cn.hutool.core.util.StrUtil;
import com.barton.sbc.annotation.SysLogAnnotation;
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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
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
    @InitBinder
    public void initBinder(WebDataBinder binder) {
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
    @SysLogAnnotation(value = "保存菜单信息",type = SysLogAnnotation.SAVE)
    public ServerResponse save(AuthPermission authPermission){
        if(authPermission == null){
            return ServerResponse.createByErrorMessage("保存失败！");
        }
        //添加
        if(StrUtil.isEmpty(authPermission.getId())){
            authPermission.setGmtCreate(new Date());
            authPermission.setGmtModified(new Date());
            authPermission.setUserCreate(currentUser.getId());
            authPermission.setUserModified(currentUser.getId());
            if((authPermissionService.insert(authPermission)) != null){
                return ServerResponse.createBySuccess("添加成功！");
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
    @SysLogAnnotation(value = "删除菜单信息",type = SysLogAnnotation.DELETE)
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
    @PostMapping("/selectPermissionsByRoleId")
    public List<AuthPermission> selectPermissionsByRoleId(@RequestParam String roleId){
        return authPermissionService.selectByRoleId(roleId);
    }

    /**
     * 查询所有权限（树型菜单）
     * @return
     */
    @PostMapping("/selectAllPermissionToTree")
    public List<AuthPermission> selectAllPermissionToTree(){
        List<AuthPermission> aps = authPermissionService.findAll();
        AuthPermission root = new AuthPermission("0",null);
        root = TreeUtil.getTree(root,aps);
        return root.getChildNodes();
    }




}
