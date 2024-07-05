package com.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.*;

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
public class Tasks implements Serializable {

    /**
     * 任务Id
     */
    @TableId(value = "task_id", type = IdType.AUTO)
    private Integer taskId;

    /**
     * 任务介绍
     */
    private String taskContent;

    /**
     * 创建日期
     */
    private Timestamp createDate;

    /**
     * 截止日期
     */
    private Timestamp expiryDate;

    /**
     * 任务进度
     */
    private Integer taskProgress;

    /**
     * 发布人Id
     */
    private Integer publishUserId;

    /**
     * 完成任务用户Id
     */
    private Integer workUserId;

    /**
     * 任务是否被删除，三天后销毁
     */
    private String taskState;

    /**
     * 任务反馈
     */
    private String taskFeedback;
}
