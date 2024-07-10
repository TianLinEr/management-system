package com.base.excepttion;

import lombok.Getter;

/**
 * 任务权限不足
 */
@Getter
public class NoAuthorityUpdateTaskException extends RuntimeException{
    private final Integer errorCode;

    private final String msg="没有权限修改该任务";

    public NoAuthorityUpdateTaskException(Integer errorCode){
        super("没有权限修改该任务");
        this.errorCode = errorCode;
    }
}
