package com.base.excepttion;

import lombok.Getter;

/**
 * 添加团队相关的操作失败
 */
@Getter
public class AddCommentException extends RuntimeException{
    private final Integer errorCode;

    private final String msg="添加评论相关的操作失败";

    public AddCommentException(Integer errorCode){
        super("添加评论相关的操作失败");
        this.errorCode = errorCode;
    }
}
