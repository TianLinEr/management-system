package com.base.excepttion;

import lombok.Getter;

/**
 * 过滤器异常
 */
@Getter
public class FilterHeaderException extends RuntimeException{

    private final Integer errorCode;

    private final String msg="未从网关发送请求";

    public FilterHeaderException(Integer errorCode){
        super("未从网关发送请求");
        this.errorCode = errorCode;
    }
}
