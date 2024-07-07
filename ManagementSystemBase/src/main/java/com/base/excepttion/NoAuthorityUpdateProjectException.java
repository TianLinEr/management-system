package com.base.excepttion;

import lombok.Getter;

/**
 * 项目权限不足
 */
@Getter
public class NoAuthorityUpdateProjectException extends RuntimeException{
    private final Integer errorCode;

    private final String msg="没有权限修改该项目";

    public NoAuthorityUpdateProjectException(Integer errorCode){
        super("没有权限修改该项目");
        this.errorCode = errorCode;
    }
}
