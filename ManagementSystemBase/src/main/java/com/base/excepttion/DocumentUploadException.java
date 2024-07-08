package com.base.excepttion;

import lombok.Getter;

/**
 * 添加团队相关的操作失败
 */
@Getter
public class DocumentUploadException extends RuntimeException{
    private final Integer errorCode;

    private final String msg="文件上传失败，可能原因是该文件名已存在，网络原因等";

    public DocumentUploadException(Integer errorCode){
        super("文件上传失败，可能原因是该文件名已存在，网络原因等");
        this.errorCode = errorCode;
    }
}
