package com.base.excepttion;

import lombok.Getter;

/**
 * 添加用户相关的操作失败
 */
@Getter
public class AddUserException extends RuntimeException{
    private final Integer errorCode;

    private final String msg="添加用户相关的操作失败";

    public AddUserException(Integer errorCode){
        super("添加用户相关的操作失败");
        this.errorCode = errorCode;
    }
}
