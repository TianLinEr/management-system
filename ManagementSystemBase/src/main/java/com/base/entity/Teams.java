package com.base.entity;

import java.sql.Timestamp;

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
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Teams {

    /**
     * 团队Id
     */
    private String teamId;

    /**
     * 团队名称
     */
    private String teamName;

    /**
     * 创建日期
     */
    private Timestamp createDate;

    /**
     * 团队是否解散，解散后三天内销毁
     */
    private String teamState;
}
