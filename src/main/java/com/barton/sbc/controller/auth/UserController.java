package com.barton.sbc.controller.auth;

import com.barton.sbc.common.ServerResponse;
import com.barton.sbc.domain.entity.auth.AuthUser;
import com.barton.sbc.service.auth.AuthUserService;
import com.barton.sbc.utils.CurrentUserUtil;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(value = "/auth/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private AuthUserService authUserService;
    @Autowired
    private AuthenticationManager authenticationManager;
    private static AuthUser currentUser;
    {
        currentUser = CurrentUserUtil.getAuthUser();
    }

    /**
     * 添加
     * @param authUser
     * @return
     */
    @PostMapping("/insert")
    public ServerResponse insert(AuthUser authUser){
        authUser.setPassword(new BCryptPasswordEncoder().encode(authUser.getUsername()));
        authUser.setLocked("0");
        authUser.setGmtCreate(new Date());
        authUser.setUserCreate(currentUser.getId());
        if((authUser = authUserService.insert(authUser)) != null){
            return ServerResponse.createBySuccess(authUser);
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
        if(authUserService.deleteById(id) > 0){
            return ServerResponse.createBySuccessMessage("删除成功");
        }
        return ServerResponse.createByErrorMessage("删除失败");
    }

    /**
     * 不可用
     * @param id
     * @return
     */
    @GetMapping("/changeLocked")
    public ServerResponse changeLocked(@RequestParam String id){
        AuthUser authUser = new AuthUser();
        authUser.setId(id);
        authUser.setLocked("1");
        authUserService.updateById(authUser);
        return ServerResponse.createByErrorMessage("修改成功");
    }


    /**
     * 修改
     * @param authUser
     * @return
     */
    @PostMapping("/update")
    public ServerResponse updateById(@RequestBody AuthUser authUser){
        authUser.setPassword(null);
        authUser.setGmtModified(new Date());
        authUser.setUserModified(currentUser.getId());
        if(authUserService.updateById(authUser) != null){
            return ServerResponse.createBySuccessMessage("修改成功");
        }
        return ServerResponse.createByErrorMessage("修改失败");
    }

    /**
     * 修改密码
     * @param authUser
     * @return
     */
    @PostMapping("/updatePassword")
    public ServerResponse updatePassword(@RequestBody AuthUser authUser){
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(authUser.getUsername(), authUser.getOriginPassword());
        Authentication authentication = authenticationManager.authenticate(upToken);
        if(authentication == null){
            return ServerResponse.createByErrorMessage("原始密码不正确");
        }
        AuthUser newUser = new AuthUser();
        newUser.setId(authUser.getId());
        newUser.setPassword(new BCryptPasswordEncoder().encode(authUser.getPassword()));
        newUser.setGmtModified(new Date());
        newUser.setUserModified(currentUser.getId());
        if(authUserService.updateById(newUser) != null){
            return ServerResponse.createBySuccessMessage("修改成功");
        }
        return ServerResponse.createByErrorMessage("修改失败");
    }

    /**
     * 查询
     * @param authUser
     * @return
     */
    @PostMapping("/selectPage")
    public PageInfo<AuthUser> selectPage(@RequestParam Integer page, @RequestParam Integer pageSize, @RequestBody AuthUser authUser){
        return authUserService.selectPage(page,pageSize,null);
    }

    /**
     * 删除用户角色关联信息
     * @param userId
     * @param roleId
     * @return
     */
    @PostMapping("/deleteUserRoleByUserIdAndRoleId")
    public ServerResponse deleteUserRoleByUserIdAndRoleId(@RequestParam String userId,@RequestParam String roleId){
        if(authUserService.deleteUserRoleByUserIdAndRoleId(userId,roleId) > 0){
            return ServerResponse.createBySuccessMessage("删除成功");
        }
        return ServerResponse.createByErrorMessage("删除失败");
    }


}
