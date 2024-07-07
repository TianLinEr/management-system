package com.http.client;


import com.base.entity.Teams;
import com.base.utils.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("http://127.0.0.1:10010/team")
public interface TeamHttp {

    /**
     * 根据Id获取团队信息
     * @param teamId
     * @return
     */
    @GetExchange("/{teamId}")
    Result<Teams> getTeam(@PathVariable String teamId);

    /**
     * 添加一个临时团队, 参数既是团队Id，又是团队名称
     * 个人直接新增一个项目，隐式新增团队
     * @param team
     */
    @PostExchange("/add/{team}")
    Result addTeam(@PathVariable String team);

    /**
     * 团队-用户表，添加一个用户到团队
     * @param teamId
     * @param id
     */
    @PostExchange("/addUser/{id}/{teamId}")
    Result addTeamUserLS(@PathVariable String id,@PathVariable String teamId);

    /**
     * 团队-用户表，得到团队里该用户权限
     * @param userId
     * @param teamId
     * @return
     */
    @GetExchange("/authority/{userId}/{teamId}")
    Integer getUserAuthority(@PathVariable Integer userId,@PathVariable String teamId);
}
