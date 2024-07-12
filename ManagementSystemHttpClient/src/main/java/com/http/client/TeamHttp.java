package com.http.client;


import com.base.annotation.NotNeedIntercept;
import com.base.utils.Result;
import com.base.vo.TeamVO;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

@FeignClient("team")
@NotNeedIntercept
public interface TeamHttp {

    /**
     * 根据Id获取团队信息
     * @param teamId
     * @return
     */
    @RequestLine("GET /sel/{teamId}")
    @NotNeedIntercept
    @Headers({"ycdy: httpService","service-info: openFeign"})
    Result<TeamVO> getTeam(@Param("teamId") String teamId);

    /**
     * 添加一个临时团队, 参数既是团队Id，又是团队名称
     * 个人直接新增一个项目，隐式新增团队
     * @param team
     */
    @RequestLine("POST /add/{team}")
    @NotNeedIntercept
    @Headers({"ycdy: httpService","service-info: openFeign"})
    Result addTeam(@Param("team") String team);

    /**
     * 团队-用户表，添加一个用户到团队
     * @param teamId
     * @param id
     */
    @RequestLine("POST /addUser/{id}/{teamId}")
    @NotNeedIntercept
    @Headers({"ycdy: httpService","service-info: openFeign"})
    Result addTeamUserLS(@Param("id") String id,@Param("teamId") String teamId);

    /**
     * 团队-用户表，得到团队里该用户权限
     * @param userId
     * @param teamId
     * @return
     */
    @RequestLine("GET /authority/{userId}/{teamId}")
    @NotNeedIntercept
    @Headers({"ycdy: httpService","service-info: openFeign"})
    Integer getUserAuthority(@Param("userId") Integer userId,@Param("teamId") String teamId);

    /**
     * 团队-用户表，删除团队
     * @param teamIds
     * @return
     */
    @RequestLine("DELETE /del-user/{teamIds}")
    @NotNeedIntercept
    @Headers({"ycdy: httpService","service-info: openFeign"})
    Result delTeamTask(@Param("teamIds") List<Integer> teamIds);
}
