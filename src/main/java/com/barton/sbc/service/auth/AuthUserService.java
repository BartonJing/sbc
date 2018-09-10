package com.barton.sbc.service.auth;

import com.barton.sbc.domain.entity.auth.AuthUser;
import com.github.pagehelper.PageInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;

public interface AuthUserService extends UserDetailsService {
    /**
     * 添加
     * @param authUser
     * @return
     */
    AuthUser insert(AuthUser authUser);

    /**
     * 根据id修改
     * @param authUser
     * @return
     */
    AuthUser updateById(AuthUser authUser);

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
    AuthUser selectById(String id);

    /**
     * 根据传入的参数查询
     * @param params
     * @return
     */
    List<AuthUser> selectByParams(Map<String, Object> params);

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @param params
     * @return
     */
    PageInfo<AuthUser> selectPage(Integer page, Integer pageSize, Map<String, Object> params);


    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 操作结果
     */
    String login(String username, String password);

    /**
     * 用户注册
     *
     * @param user 用户信息
     * @return 操作结果
     */
    String register(AuthUser user);

    /**
     * 刷新密钥
     *
     * @param oldToken 原密钥
     * @return 新密钥
     */
    String refreshToken(String oldToken);
}
