package com.base.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotation.TableId;
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
public class Teams implements Serializable {

    /**
     * 团队Id
     */
    @TableId(value = "team_id")
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
