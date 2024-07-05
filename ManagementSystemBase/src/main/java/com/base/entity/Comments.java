package com.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>
 * 
 * </p>
 *
 * @author psl
 * @since 2024-07-03
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Comments implements Serializable {

    /**
     * 评论Id
     */
    @TableId(value = "comment_id", type = IdType.AUTO)
    private Integer commentId;

    /**
     * 被评论的objectId
     */
    private Integer objectId;

    /**
     * 被评论的类别，项目，任务，文档等
     */
    private Integer objectType;

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
    private Integer userId;

    /**
     * 评论是否被删除，三天后销毁
     */
    private String commentState;
}
