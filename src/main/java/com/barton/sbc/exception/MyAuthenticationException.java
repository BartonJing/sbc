package com.barton.sbc.exception;

import org.springframework.security.core.AuthenticationException;

public class MyAuthenticationException extends AuthenticationException {
    private int code;

    public MyAuthenticationException(String msg) {
        super(msg);
    }

    public MyAuthenticationException(int code,String msg) {
        super(msg);
        this.code = code;
    }


    public MyAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
