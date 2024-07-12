package com.base.vo;

import com.base.entity.Teams;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProjectVO {

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
     * 团队详情
     */
    private TeamVO team;

    /**
     * 创建日期
     */
    private String createDate;

    /**
     * 项目是否被删除，三天后销毁
     */
    private String projectState;

}
