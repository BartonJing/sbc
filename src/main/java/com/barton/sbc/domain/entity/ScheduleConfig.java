package com.barton.sbc.domain.entity;

import java.util.Date;

public class ScheduleConfig extends BaseDomain {
    /**
     * 
     */
    private String id;

    /**
     * 定时任务执行器
     */
    private String scheduleClass;

    /**
     * 是否 开启 kind = YN
     */
    private String scheduleStatus;

    /**
     * 定时任务执行策略   cron表达式
     */
    private String scheduleCron;

    /**
     *  描述 
     */
    private String scheduleRemark;

    /**
     * 扩展字段1
     */
    private String field1;

    /**
     * 扩展字段2
     */
    private String field2;

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

    public String getScheduleClass() {
        return scheduleClass;
    }

    public void setScheduleClass(String scheduleClass) {
        this.scheduleClass = scheduleClass == null ? null : scheduleClass.trim();
    }

    public String getScheduleStatus() {
        return scheduleStatus;
    }

    public void setScheduleStatus(String scheduleStatus) {
        this.scheduleStatus = scheduleStatus == null ? null : scheduleStatus.trim();
    }

    public String getScheduleCron() {
        return scheduleCron;
    }

    public void setScheduleCron(String scheduleCron) {
        this.scheduleCron = scheduleCron == null ? null : scheduleCron.trim();
    }

    public String getScheduleRemark() {
        return scheduleRemark;
    }

    public void setScheduleRemark(String scheduleRemark) {
        this.scheduleRemark = scheduleRemark == null ? null : scheduleRemark.trim();
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1 == null ? null : field1.trim();
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2 == null ? null : field2.trim();
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