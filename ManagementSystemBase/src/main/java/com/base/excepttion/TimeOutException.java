package com.base.excepttion;

import lombok.Getter;

@Getter
public class TimeOutException extends RuntimeException {
    private final Integer errorCode;

    private final String msg="验证码已销毁";

    public TimeOutException(Integer errorCode){
        super("验证码已销毁");
        this.errorCode = errorCode;
    }
}
