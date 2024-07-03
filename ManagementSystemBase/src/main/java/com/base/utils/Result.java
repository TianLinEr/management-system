package com.base.utils;

import lombok.*;

import java.util.List;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
/**
 * 返回前端结果
 */
public class Result<T> {

    /**
     * 返回前端编号
     * 如：
     * 成功：200
     * 不能访问：404
     * 失败：500
     */
    private int code;

    /**
     * 返回前端的消息
     */
    private String msg;

    /**
     * 返回前端的数据
     */
    private List<T> res;

    public Result<T> error(int code,String msg){
        return new Result<T>(code,msg,null);
    }

    public Result<T> success(int code, String msg, List<T> res){
        return new Result<T>(code,msg,res);
    }
}
