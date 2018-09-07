package com.barton.sbc.controller.auth;

import com.barton.sbc.common.ServerResponse;
import com.barton.sbc.domain.entity.auth.AuthUser;
import com.barton.sbc.service.auth.AuthUserService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private AuthUserService authUserService;

    /**
     * 添加
     * @param authUser
     * @return
     */
    @PostMapping("/insert")
    public ServerResponse insert(AuthUser authUser){
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
     * 修改
     * @param authUser
     * @return
     */
    @PostMapping("/update")
    public ServerResponse updateById(@RequestBody AuthUser authUser){
        if((authUser = authUserService.updateById(authUser)) != null){
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


}
