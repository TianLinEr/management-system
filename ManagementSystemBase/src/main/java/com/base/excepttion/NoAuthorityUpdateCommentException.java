package com.base.excepttion;

import lombok.Getter;

/**
 * 评论权限不足
 */
@Getter
public class NoAuthorityUpdateCommentException extends RuntimeException{
    private final Integer errorCode;

    private final String msg="没有权限修改该评论";

    public NoAuthorityUpdateCommentException(Integer errorCode){
        super("没有权限修改该评论");
        this.errorCode = errorCode;
    }
}
