package com.barton.sbc.domain.entity;

public class Encode extends BaseDomain {
    /**
     * 
     */
    private Integer id;

    /**
     * 类型
     */
    private String kind;

    /**
     * 类型名称
     */
    private String kindName;

    /**
     * 编码
     */
    private String encode;

    /**
     * 编码名称
     */
    private String encodeName;

    /**
     * 父亲节点
     */
    private String parentId;

    /**
     * 是否可用
     */
    private String enable;

    /**
     * 排序号
     */
    private Integer orderId;

    /**
     * 简拼
     */
    private String simpleSpelling;

    /**
     * 全拼
     */
    private String fullSpelling;

    /**
     * 扩展字段
     */
    private String extendA;

    /**
     * 扩展字段
     */
    private String extendB;

    /**
     * 扩展字段
     */
    private String extendC;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind == null ? null : kind.trim();
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName == null ? null : kindName.trim();
    }

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode == null ? null : encode.trim();
    }

    public String getEncodeName() {
        return encodeName;
    }

    public void setEncodeName(String encodeName) {
        this.encodeName = encodeName == null ? null : encodeName.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable == null ? null : enable.trim();
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getSimpleSpelling() {
        return simpleSpelling;
    }

    public void setSimpleSpelling(String simpleSpelling) {
        this.simpleSpelling = simpleSpelling == null ? null : simpleSpelling.trim();
    }

    public String getFullSpelling() {
        return fullSpelling;
    }

    public void setFullSpelling(String fullSpelling) {
        this.fullSpelling = fullSpelling == null ? null : fullSpelling.trim();
    }

    public String getExtendA() {
        return extendA;
    }

    public void setExtendA(String extendA) {
        this.extendA = extendA == null ? null : extendA.trim();
    }

    public String getExtendB() {
        return extendB;
    }

    public void setExtendB(String extendB) {
        this.extendB = extendB == null ? null : extendB.trim();
    }

    public String getExtendC() {
        return extendC;
    }

    public void setExtendC(String extendC) {
        this.extendC = extendC == null ? null : extendC.trim();
    }
}