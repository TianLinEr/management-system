package com.base.vo;

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
public class TaskVO implements Serializable {

    /**
     * 任务Id
     */
    private Integer taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务介绍
     */
    private String taskContent;

    /**
     * 创建日期
     */
    private String createDate;

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
     * 任务状态
     */
    private String taskStatus;

    /**
     * 任务是否公开
     */
    private String taskType;

    /**
     * 任务反馈
     */
    private String taskFeedback;
}
