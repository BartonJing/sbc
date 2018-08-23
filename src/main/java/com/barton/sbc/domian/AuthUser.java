package com.barton.sbc.domian;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * create by barton on 2018-7-3
 */
public class AuthUser  implements Serializable,UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(findMenu("").getPath()));
        authorities.add(new SimpleGrantedAuthority(findMenu("").getPath()));
        authorities.add(new SimpleGrantedAuthority(findMenu("").getPath()));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private String id;
    /**
     * 用户名称
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    private String test;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public AuthUser(String username, String password,String test) {
        this.username = username;
        this.password = password;
        this.test = test;
    }
    public AuthUser(AuthUser authUser) {
        this.username = authUser.getUsername();
        this.password = authUser.getPassword();
        this.test = test;
    }
    public AuthUser() {
    }

    @Override
    public String toString() {
        return "AuthUser{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    /**
     * 模拟从数据库等持久化存储中取出对应的用户信息
     * @param username
     * @return
     */
    public AuthUser findUSer(String username){
        if(!username.equals("admin")){
            return null;
        }
        System.out.println("*****************   "+username);
        String finalSecret = new BCryptPasswordEncoder().encode("123456");
        System.out.println(finalSecret);
        return new AuthUser("admin",finalSecret,"testsss");
    }

    /**
     * 模拟从数据库等持久化存储中取出用户对应的权限信息
     * @param username
     * @return
     */
    public Permission findMenu(String username){
        Permission permission = new Permission();
        permission.setEnable(true);
        permission.setId("iddd");
        permission.setIoc("test");
        permission.setPath("/index");
        return permission;
    }
}
