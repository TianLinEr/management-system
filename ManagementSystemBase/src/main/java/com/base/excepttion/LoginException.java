package com.base.excepttion;

import lombok.Getter;

/**
 * 用户未登录异常
 */
@Getter
public class LoginException extends RuntimeException{
    private final Integer errorCode;

    private final String msg="没有登录，请登录";

    public LoginException(Integer errorCode){
        super("没有登录，请登录");
        this.errorCode = errorCode;
    }
}
