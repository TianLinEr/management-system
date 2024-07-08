package com.base.excepttion;

import lombok.Getter;

/**
 * 文档权限不足
 */
@Getter
public class NoAuthorityUpdateDocumentException extends RuntimeException{
    private final Integer errorCode;

    private final String msg="没有权限修改该文档";

    public NoAuthorityUpdateDocumentException(Integer errorCode){
        super("没有权限修改该文档");
        this.errorCode = errorCode;
    }
}
