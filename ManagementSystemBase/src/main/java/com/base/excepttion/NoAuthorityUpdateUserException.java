package com.base.excepttion;

import lombok.Getter;

/**
 * 用户权限不足
 */
@Getter
public class NoAuthorityUpdateUserException extends RuntimeException{
    private final Integer errorCode;

    private final String msg="没有权限修改该用户";

    public NoAuthorityUpdateUserException(Integer errorCode){
        super("没有权限修改该用户");
        this.errorCode = errorCode;
    }
}
