package com.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("project_task")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProjectTask {

    /**
     * 系统Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 项目Id
     */
    private Integer projectId;

    /**
     * 任务Id
     */
    private Integer taskId;

    /**
     * 任务状态，已完成，未完成，待审核
     */
    private Integer taskStatus;

    /**
     * 任务是否公开
     */
    private String taskType;
}
