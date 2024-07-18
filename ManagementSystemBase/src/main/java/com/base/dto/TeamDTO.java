package com.base.dto;

import lombok.*;

import java.io.Serializable;
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
public class TeamDTO implements Serializable {

    /**
     * 团队名称
     */
    private String teamName;


    /**
     * 团队成员
     */
    private List<Integer> memberList;

}
