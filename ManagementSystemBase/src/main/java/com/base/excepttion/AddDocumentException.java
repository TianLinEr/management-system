package com.base.excepttion;

import lombok.Getter;

/**
 * 添加文件相关的操作失败
 */
@Getter
public class AddDocumentException extends RuntimeException{
    private final Integer errorCode;

    private final String msg="添加文件相关的操作失败";

    public AddDocumentException(Integer errorCode){
        super("添加文件相关的操作失败");
        this.errorCode = errorCode;
    }
}
