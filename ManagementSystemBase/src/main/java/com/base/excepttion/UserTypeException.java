package com.base.excepttion;

import lombok.Getter;

/**
 * 用户类型异常
 */
@Getter
public class UserTypeException extends RuntimeException{
    private final Integer errorCode;

    private final String msg="没有管理员权限";

    public UserTypeException(Integer errorCode){
        super("没有管理员权限");
        this.errorCode = errorCode;
    }
}
