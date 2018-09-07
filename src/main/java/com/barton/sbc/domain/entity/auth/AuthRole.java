package com.barton.sbc.domain.entity.auth;

import com.barton.sbc.domain.entity.BaseDomain;

import java.util.Date;

public class AuthRole extends BaseDomain {
    /**
     * 编号
     */
    private String id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色代码
     */
    private String roleCode;

    /**
     * 
     */
    private Integer level;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 最后修改时间
     */
    private Date gmtModified;

    /**
     * 
     */
    private String userCreate;

    /**
     * 
     */
    private String userModified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode == null ? null : roleCode.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getUserCreate() {
        return userCreate;
    }

    public void setUserCreate(String userCreate) {
        this.userCreate = userCreate == null ? null : userCreate.trim();
    }

    public String getUserModified() {
        return userModified;
    }

    public void setUserModified(String userModified) {
        this.userModified = userModified == null ? null : userModified.trim();
    }

    @Override
    public String toString() {
        return "AuthRole{" +
                "id='" + id + '\'' +
                ", roleName='" + roleName + '\'' +
                ", roleCode='" + roleCode + '\'' +
                ", level=" + level +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", userCreate='" + userCreate + '\'' +
                ", userModified='" + userModified + '\'' +
                '}';
    }
}