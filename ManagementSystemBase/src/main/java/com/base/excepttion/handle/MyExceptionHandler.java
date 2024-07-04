package com.base.excepttion.handle;

import com.base.excepttion.FilterHeaderException;
import com.base.utils.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(FilterHeaderException.class)
    public ResponseEntity<Object> handleMyCustomException(FilterHeaderException ex) {
        // 创建你的响应体
        Result<Object> result = new Result<>().error(ex.getErrorCode(), ex.getMsg());

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(ex.getErrorCode()));
    }

}
