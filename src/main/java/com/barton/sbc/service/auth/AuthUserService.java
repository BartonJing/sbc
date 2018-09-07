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
}
