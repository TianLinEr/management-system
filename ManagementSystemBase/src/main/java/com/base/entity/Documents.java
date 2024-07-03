package com.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.sql.Timestamp;
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
public class Documents {

    /**
     * 文档Id
     */
    @TableId(value = "document_id", type = IdType.AUTO)
    private Integer documentId;

    /**
     * 文档名称
     */
    private String documentName;

    /**
     * 文档地址
     */
    private String documentUrl;

    /**
     * 文档发布人Id
     */
    private Integer userId;

    /**
     * 发布日期
     */
    private Timestamp createDate;

    /**
     * 文档是否被删除，三天后销毁
     */
    private String documentState;
}
