package com.barton.sbc.controller.auth;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.barton.sbc.annotation.SysLog;
import com.barton.sbc.common.ServerResponse;
import com.barton.sbc.dao.auth.AuthRolePermissionMapper;
import com.barton.sbc.dao.auth.AuthUserRoleMapper;
import com.barton.sbc.domain.entity.auth.AuthPermission;
import com.barton.sbc.domain.entity.auth.AuthRolePermission;
import com.barton.sbc.domain.entity.auth.AuthUser;
import com.barton.sbc.domain.entity.auth.AuthUserRole;
import com.barton.sbc.service.auth.AuthPermissionService;
import com.barton.sbc.service.auth.AuthUserService;
import com.barton.sbc.utils.CurrentUserUtil;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/auth/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private AuthUserService authUserService;
    @Autowired
    private AuthUserRoleMapper authUserRoleMapper;
    @Autowired
    private AuthenticationManager authenticationManager;
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
     * @param authUser
     * @return
     */
    @PostMapping("/save")
    @SysLog(value = "保存用户信息", type = SysLog.SysLogType.SAVE)
    public ServerResponse insert(@Validated @RequestBody AuthUser authUser) {
        if (authUser == null) {
            return ServerResponse.createByErrorMessage("保存失败，参数为空");
        }
        //添加
        if (StrUtil.isEmpty(authUser.getId())) {
            //判断此用户是否已经添加
            AuthUser authUserOld = authUserService.selectByUserName(authUser.getUsername());
            if (authUserOld != null) {
                return ServerResponse.createByErrorMessage("添加失败，此用户已经存在");
            }
            authUser.setId(RandomUtil.randomUUID());
            authUser.setPassword(new BCryptPasswordEncoder().encode(authUser.getPassword()));
            authUser.setGmtCreate(new Date());
            authUser.setUserCreate(currentUser.getId());
            authUser.setGmtModified(new Date());
            authUser.setUserModified(currentUser.getId());
            authUserService.insert(authUser);
        } else {//修改
            authUser.setPassword(null);
            authUser.setGmtModified(new Date());
            authUser.setUserModified(currentUser.getId());
            authUserService.updateById(authUser);
        }
        return ServerResponse.createBySuccessMessage("保存成功！");
    }


    /**
     * 删除
     *
     * @param id
     * @return
     */
    @GetMapping("/delete")
    public ServerResponse deleteById(@RequestParam String id) {
        if (authUserService.deleteById(id) > 0) {
            return ServerResponse.createBySuccessMessage("删除成功");
        }
        return ServerResponse.createByErrorMessage("删除失败");
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping("/selectById")
    @ResponseBody
    public ServerResponse selectById(@RequestParam String id) {
        AuthUser authUser = authUserService.selectById(id);
        if(authUser == null){
            ServerResponse.createByErrorMessage("没有此用户信息");
        }
        authUser.setPassword("XXXX");
        authUser.setPasswordC("XXXX");
        return ServerResponse.createBySuccess(authUser);
    }

    /**
     * 锁定账户
     *
     * @param id
     * @return
     */
    @PostMapping("/changeLocked")
    public ServerResponse changeLocked(@RequestParam String id, @RequestParam String locked) {
        AuthUser authUser = new AuthUser();
        authUser.setId(id);
        authUser.setLocked(locked);
        authUserService.updateById(authUser);
        return ServerResponse.createBySuccessMessage("修改成功");
    }

    /**
     * 修改密码
     *
     * @param authUser
     * @return
     */
    @PostMapping("/updatePassword")
    public ServerResponse updatePassword(@RequestBody AuthUser authUser) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(authUser.getUsername(), authUser.getOriginPassword());
        Authentication authentication = authenticationManager.authenticate(upToken);
        if (authentication == null) {
            return ServerResponse.createByErrorMessage("原始密码不正确");
        }
        AuthUser newUser = new AuthUser();
        newUser.setId(authUser.getId());
        newUser.setPassword(new BCryptPasswordEncoder().encode(authUser.getPassword()));
        newUser.setGmtModified(new Date());
        newUser.setUserModified(currentUser.getId());
        if (authUserService.updateById(newUser) != null) {
            return ServerResponse.createBySuccessMessage("修改成功");
        }
        return ServerResponse.createByErrorMessage("修改失败");
    }

    /**
     * 查询
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/selectPage")
    public PageInfo<AuthUser> selectPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return authUserService.selectPage(pageNum, pageSize, null);
    }

    /**
     * 删除用户角色关联信息
     *
     * @param userId
     * @param roleId
     * @return
     */
    @PostMapping("/deleteUserRoleByUserIdAndRoleId")
    public ServerResponse deleteUserRoleByUserIdAndRoleId(@RequestParam String userId, @RequestParam String roleId) {
        if (authUserService.deleteUserRoleByUserIdAndRoleId(userId, roleId) > 0) {
            return ServerResponse.createBySuccessMessage("删除成功");
        }
        return ServerResponse.createByErrorMessage("删除失败");
    }

    /**
     * 添加用户对应的角色信息
     *
     * @param roles
     * @param userId
     * @return
     */
    @PostMapping("/saveUserRoles")
    @Transactional
    public ServerResponse saveUserRoles(@RequestParam(value = "roles") String[] roles, @RequestParam String userId) {
        if (StrUtil.isEmpty(userId)) {
            ServerResponse.createByErrorMessage("数据错误，请联系管理员");
        }
        authUserRoleMapper.deleteByUserId(userId);
        authUserService.deleteUserRoleById(userId);
        List<AuthUserRole> authUserRoles = new ArrayList<AuthUserRole>();
        if (roles != null && roles.length > 0) {
            for (String roleId : roles) {
                AuthUserRole aur = new AuthUserRole();
                aur.setId(RandomUtil.randomUUID());
                aur.setRoleId(roleId);
                aur.setUserId(userId);
                aur.setGmtCreate(new Date());
                aur.setGmtModified(new Date());
                authUserRoles.add(aur);
            }
        }
        if (authUserRoles.size() > 0) {
            authUserService.batchInsertUserRole(authUserRoles);
        }
        return ServerResponse.createBySuccessMessage("保存成功！");
    }

}
