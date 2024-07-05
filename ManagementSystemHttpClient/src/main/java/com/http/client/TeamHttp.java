package com.http.client;


import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/team")
public interface TeamHttp {

    /**
     * 添加一个临时团队, 参数既是团队Id，又是团队名称
     * 个人直接新增一个项目，隐式新增团队
     * @param team
     */
    @PostExchange("/add")
    void addTeam(String team);

    /**
     * 团队-用户表，添加一个用户到团队
     * @param teamId
     * @param id
     */
    void addTeamUser(String teamId, String id);
}
