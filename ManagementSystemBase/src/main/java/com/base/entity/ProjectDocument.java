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
@TableName("project_document")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProjectDocument {

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
     * 文档Id
     */
    private Integer documentId;

    /**
     * 文档是否被公开
     */
    private String documentType;
}
