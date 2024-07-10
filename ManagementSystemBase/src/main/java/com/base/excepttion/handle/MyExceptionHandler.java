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
    public ResponseEntity<Object> handleMyCustomException6(AddCommentException ex) {
        // 创建你的响应体
        Result<Object> result = new Result<>().error(ex.getErrorCode(), ex.getMsg());

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(ex.getErrorCode()));
    }

    @ExceptionHandler(NoAuthorityUpdateCommentException.class)
    public ResponseEntity<Object> handleMyCustomException7(NoAuthorityUpdateCommentException ex) {
        // 创建你的响应体
        Result<Object> result = new Result<>().error(ex.getErrorCode(), ex.getMsg());

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(ex.getErrorCode()));
    }

    @ExceptionHandler(DocumentUploadException.class)
    public ResponseEntity<Object> handleMyCustomException8(DocumentUploadException ex) {
        // 创建你的响应体
        Result<Object> result = new Result<>().error(ex.getErrorCode(), ex.getMsg());

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(ex.getErrorCode()));
    }

    @ExceptionHandler(AddDocumentException.class)
    public ResponseEntity<Object> handleMyCustomException9(AddDocumentException ex) {
        // 创建你的响应体
        Result<Object> result = new Result<>().error(ex.getErrorCode(), ex.getMsg());

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(ex.getErrorCode()));
    }

    @ExceptionHandler(NoAuthorityUpdateDocumentException.class)
    public ResponseEntity<Object> handleMyCustomException10(NoAuthorityUpdateDocumentException ex) {
        // 创建你的响应体
        Result<Object> result = new Result<>().error(ex.getErrorCode(), ex.getMsg());

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(ex.getErrorCode()));
    }

    @ExceptionHandler(AddUserException.class)
    public ResponseEntity<Object> handleMyCustomException11(AddUserException ex) {
        // 创建你的响应体
        Result<Object> result = new Result<>().error(ex.getErrorCode(), ex.getMsg());

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(ex.getErrorCode()));
    }

    @ExceptionHandler(NoAuthorityUpdateUserException.class)
    public ResponseEntity<Object> handleMyCustomException12(NoAuthorityUpdateUserException ex) {
        // 创建你的响应体
        Result<Object> result = new Result<>().error(ex.getErrorCode(), ex.getMsg());

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(ex.getErrorCode()));
    }

    @ExceptionHandler(NoAuthorityUpdateTeamException.class)
    public ResponseEntity<Object> handleMyCustomException13(NoAuthorityUpdateTeamException ex) {
        // 创建你的响应体
        Result<Object> result = new Result<>().error(ex.getErrorCode(), ex.getMsg());

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(ex.getErrorCode()));
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<Object> handleMyCustomException14(LoginException ex) {
        // 创建你的响应体
        Result<Object> result = new Result<>().error(ex.getErrorCode(), ex.getMsg());

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(ex.getErrorCode()));
    }

    @ExceptionHandler(TimeOutException.class)
    public ResponseEntity<Object> handleMyCustomException15(TimeOutException ex) {
        // 创建你的响应体
        Result<Object> result = new Result<>().error(ex.getErrorCode(), ex.getMsg());

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(ex.getErrorCode()));
    }

    @ExceptionHandler(NoAuthorityUpdateTaskException.class)
    public ResponseEntity<Object> handleMyCustomException16(NoAuthorityUpdateTaskException ex) {
        // 创建你的响应体
        Result<Object> result = new Result<>().error(ex.getErrorCode(), ex.getMsg());

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(ex.getErrorCode()));
    }

    @ExceptionHandler(AddTaskException.class)
    public ResponseEntity<Object> handleMyCustomException17(AddTaskException ex) {
        // 创建你的响应体
        Result<Object> result = new Result<>().error(ex.getErrorCode(), ex.getMsg());

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(ex.getErrorCode()));
    }

}
