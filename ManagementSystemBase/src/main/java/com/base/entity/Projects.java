package com.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

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
public class Projects {

    /**
     * 项目Id
     */
    @TableId(value = "project_id", type = IdType.AUTO)
    private Integer projectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 项目介绍
     */
    private String projectContent;

    /**
     * 团队Id
     */
    private Integer teamId;

    /**
     * 项目是否被公开
     */
    private String projectType;

    /**
     * 项目是否被删除，三天后销毁
     */
    private String projectState;
}
