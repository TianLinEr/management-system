package com.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

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
@TableName("team_user")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeamUser implements Serializable {

    /**
     * 系统Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 团队Id
     */
    private Integer teamId;

    /**
     * 用户Id
     */
    private Integer userId;

    /**
     * 用户权限
     */
    private String authority;
}
