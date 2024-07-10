package com.base.vo;

import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

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
public class TeamsVO implements Serializable {

    /**
     * 团队名称
     */
    private String teamName;


    /**
     * 团队成员
     */
    private List<Integer> memberList;

}
