package com.base.vo;

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
public class DocumentVO implements Serializable {

    /**
     * 文档Id
     */
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
    private UserVO userVO;

    /**
     * 发布日期
     */
    private String createDate;

    /**
     * 文档是否被删除，三天后销毁
     */
    private String documentState;

    /**
     * 项目Id
     */
    private Integer projectId;

    /**
     * 文档是否被公开
     */
    private String documentType;
}
