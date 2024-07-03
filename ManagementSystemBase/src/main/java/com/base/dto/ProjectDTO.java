package com.base.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.base.entity.Projects;
import com.base.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO{

    /**
     * 项目Id
     */
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
     * 团队名称
     */
    private String teamName;

    /**
     * 创建日期
     */
    private Timestamp createDate;

    /**
     * 团队成员
     */
    private List<Users> teamUsers;

}
