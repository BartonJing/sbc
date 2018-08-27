package com.barton.sbc.common;

public enum ResponseCode {

    SUCCESS(0, "SUCCESS"),
    ERROR(1, "ERROR"),
    LOGINSUCCESS(-11, "LOGINSUCCESS"),//登录成功
    LOGINERROR(-12, "LOGINERROR");//登录失败


    private final int code;
    private final String desc;


    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
