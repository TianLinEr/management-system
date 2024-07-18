package com.base.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TeamVO {

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
    private String createDate;

    /**
     * 团队是否解散，解散后三天内销毁
     */
    private String teamState;

    /**
     * 团队成员列表
     */
    private List<UserVO> memberList;
}
