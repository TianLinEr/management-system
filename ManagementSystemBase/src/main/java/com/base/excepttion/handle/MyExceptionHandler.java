package com.base.excepttion.handle;

import com.base.excepttion.*;
import com.base.utils.Result;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

/**
 * 异常处理
 */
@RestControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(FilterHeaderException.class)
    public ResponseEntity<Object> handleMyCustomException1(FilterHeaderException ex) {
        // 创建你的响应体
        Result<Object> result = new Result<>().error(ex.getErrorCode(), ex.getMsg());

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(ex.getErrorCode()));
    }

    @ExceptionHandler(NoAuthorityUpdateProjectException.class)
    public ResponseEntity<Object> handleMyCustomException2(NoAuthorityUpdateProjectException ex) {
        // 创建你的响应体
        Result<Object> result = new Result<>().error(ex.getErrorCode(), ex.getMsg());

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(ex.getErrorCode()));
    }

    @ExceptionHandler(UserTypeException.class)
    public ResponseEntity<Object> handleMyCustomException3(UserTypeException ex) {
        // 创建你的响应体
        Result<Object> result = new Result<>().error(ex.getErrorCode(), ex.getMsg());

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(ex.getErrorCode()));
    }

    @ExceptionHandler(AddTeamException.class)
    public ResponseEntity<Object> handleMyCustomException4(AddTeamException ex) {
        // 创建你的响应体
        Result<Object> result = new Result<>().error(ex.getErrorCode(), ex.getMsg());

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(ex.getErrorCode()));
    }

    @ExceptionHandler(SqlSelectException.class)
    public ResponseEntity<Object> handleMyCustomException5(SqlSelectException ex) {
        // 创建你的响应体
        Result<Object> result = new Result<>().error(ex.getErrorCode(), ex.getMsg());

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(ex.getErrorCode()));
    }

    @ExceptionHandler(AddCommentException.class)
    public ResponseEntity<Object> handleMyCustomException5(AddCommentException ex) {
        // 创建你的响应体
        Result<Object> result = new Result<>().error(ex.getErrorCode(), ex.getMsg());

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(ex.getErrorCode()));
    }

    @ExceptionHandler(NoAuthorityUpdateCommentException.class)
    public ResponseEntity<Object> handleMyCustomException5(NoAuthorityUpdateCommentException ex) {
        // 创建你的响应体
        Result<Object> result = new Result<>().error(ex.getErrorCode(), ex.getMsg());

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(ex.getErrorCode()));
    }
}
