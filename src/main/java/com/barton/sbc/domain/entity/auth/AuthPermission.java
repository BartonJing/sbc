package com.barton.sbc.domain.entity.auth;

import com.barton.sbc.utils.InheritedNode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AuthPermission implements InheritedNode<AuthPermission> {
    /**
     * 权限编号
     */
    private String id;

    /**
     * 权限名称
     */
    @NotEmpty
    private String name;

    /**
     * 权限类型
     */
    @NotEmpty
    private String type;

    /**
     * 图标名称
     */
    @NotEmpty
    private String icon;

    /**
     * vue 模块
     */
    @NotEmpty
    private String component;

    /**
     * 路径匹配规则
     */
    @NotEmpty
    private String url;

    /**
     * q前端访问路径
     */
    @NotEmpty
    private String path;

    /**
     * 父级菜单编号
     */
    @NotEmpty
    private String parentId;

    /**
     * 序号
     */
    @NotNull
    private Integer orderId;

    /**
     *
     */
    private Integer keepAlive;

    /**
     *
     */
    private Integer requireAuth;

    /**
     *
     */
    private Date gmtCreate;

    /**
     *
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


    private List<AuthPermission> children;

    public AuthPermission() {
    }

    public AuthPermission(String id, String parentId) {
        this.id = id;
        this.parentId = parentId;
    }

    @Override
    public boolean isChildFrom(AuthPermission node) {
        return this.parentId.equals(node.getId());
    }

    @Override
    public boolean isBrother(AuthPermission node) {
        return this.parentId.equals(node.getParentId());
    }

    @Override
    public void addChildNode(AuthPermission node) {
        if(children == null) {
            children = new ArrayList<AuthPermission>();
        }
        children.add(node);
    }

    @Override
    public List<AuthPermission> getChildNodes() {
        return children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component == null ? null : component.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(Integer keepAlive) {
        this.keepAlive = keepAlive;
    }

    public Integer getRequireAuth() {
        return requireAuth;
    }

    public void setRequireAuth(Integer requireAuth) {
        this.requireAuth = requireAuth;
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
}