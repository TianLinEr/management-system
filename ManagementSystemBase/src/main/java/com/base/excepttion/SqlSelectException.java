package com.base.excepttion;

import lombok.Getter;

@Getter
public class SqlSelectException extends RuntimeException {
    private final Integer errorCode;

    private final String msg="数据库查询异常，如未查询到数据等";

    public SqlSelectException(Integer errorCode){
        super("数据库查询异常，如未查询到数据等");
        this.errorCode = errorCode;
    }
}
