package com.barton.sbc.common;

public enum ResponseCode {

    SUCCESS(0, "SUCCESS"),
    ERROR(1, "ERROR"),
    LOGINSUCCESS(-11, "LOGINSUCCESS"),
    LOGINERROR(-12, "LOGINERROR"),
    EXCEPTION(-13, "EXCEPTION");


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
