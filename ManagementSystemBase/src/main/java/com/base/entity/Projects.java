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
public class Projects implements Serializable {

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
    private String teamId;

    /**
     * 项目状态
     */
    private String projectType;

    /**
     * 项目是否被删除，三天后销毁
     */
    private String projectState;

    /**
     * 项目创建日期
     */
    private Timestamp projectCreate;
}
