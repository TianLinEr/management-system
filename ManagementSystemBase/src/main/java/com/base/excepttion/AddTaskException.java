package com.base.excepttion;

import lombok.Getter;

/**
 * 添加任务相关的操作失败
 */
@Getter
public class AddTaskException extends RuntimeException{
    private final Integer errorCode;

    private final String msg="添加任务相关的操作失败";

    public AddTaskException(Integer errorCode){
        super("添加任务相关的操作失败");
        this.errorCode = errorCode;
    }
}
