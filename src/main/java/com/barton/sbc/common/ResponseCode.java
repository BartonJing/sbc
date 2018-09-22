package com.barton.sbc.common;

public enum ResponseCode {

    SUCCESS(0, "SUCCESS"),
    ERROR(1, "ERROR"),
    AUTHERROR(-11, "AUTHERROR"),//session 验证失败
    MEEDLOGIN(-12, "MEEDLOGIN"),//需要登录后才能访问
    NOPERMISSION(403, "NOPERMISSION");//没有权限


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
