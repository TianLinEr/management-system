package com.base.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.base.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO<T> {
    /**
     * 评论Id
     */
    private Integer commentId;

    /**
     * 被评论的object
     */
    private T object;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 评论日期
     */
    private Timestamp createDate;

    /**
     * 评论人
     */
    private Users user;

    /**
     * 评论是否被删除，三天后销毁
     */
    private String commentState;
}
