package com.barton.sbc.domain.entity;

import com.barton.sbc.domain.entity.auth.*;

import java.util.List;

public class AuthExtend {
    private AuthUser authUser;
    private List<AuthUserRole> userRoles;
    private AuthRolePermission rolePermissions;
    private List<AuthRole> roles;
    private List<AuthPermission> permissions;

    public AuthUser getAuthUser() {
        return authUser;
    }

    public void setAuthUser(AuthUser authUser) {
        this.authUser = authUser;
    }

    public List<AuthUserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<AuthUserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public AuthRolePermission getRolePermissions() {
        return rolePermissions;
    }

    public void setRolePermissions(AuthRolePermission rolePermissions) {
        this.rolePermissions = rolePermissions;
    }

    public List<AuthRole> getRoles() {
        return roles;
    }

    public void setRoles(List<AuthRole> roles) {
        this.roles = roles;
    }

    public List<AuthPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<AuthPermission> permissions) {
        this.permissions = permissions;
    }
}
