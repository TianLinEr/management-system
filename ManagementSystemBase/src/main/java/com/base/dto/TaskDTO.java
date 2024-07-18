package com.base.dto;

import lombok.*;

import java.io.Serializable;

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
public class TaskDTO implements Serializable {

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务介绍
     */
    private String taskContent;

    /**
     * 任务量
     */
    private Integer days;

    /**
     * 项目Id
     */
    private Integer projectId;

    /**
     * 发布人Id
     */
    private Integer publishUserId;

    /**
     * 完成任务用户Id
     */
    private Integer workUserId;

}
