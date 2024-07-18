package com.base.excepttion;

import lombok.Getter;

/**
 * 团队权限不足
 */
@Getter
public class NoAuthorityUpdateTeamException extends RuntimeException{
    private final Integer errorCode;

    private final String msg="没有权限修改该团队";

    public NoAuthorityUpdateTeamException(Integer errorCode){
        super("没有权限修改该团队");
        this.errorCode = errorCode;
    }
}
