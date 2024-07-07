package com.team.controller;

import com.base.content.ContentBase;
import com.base.entity.Teams;
import com.base.utils.Result;
import com.service.service.TeamsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@Tag(name = "团队管理")
@Slf4j
public class TeamController {

    @Autowired
    private TeamsService teamService;

    @PostMapping("/add/{team}")
    @Operation(summary = "添加团队")
    public Result addTeam(@PathVariable String team){
        log.info("团队管理-（隐式）添加团队-添加成功");
        teamService.addTeam(team);
        return new Result().success(ContentBase.SuccessCode,"添加成功",null);
    }

    @PostMapping("/addUser/{id}/{teamId}")
    @Operation(summary = "添加团队队员")
    public Result addTeamUserLS(@PathVariable String id,@PathVariable String teamId){
        log.info("团队管理-添加团队队员-添加成功");
        teamService.addTeamUserLS(teamId,id);
        return new Result().success(ContentBase.SuccessCode,"添加成功",null);
    }

    @GetMapping("/authority/{userId}/{teamId}")
    @Operation(summary = "获取团队里某个用户权限")
    public Integer getUserAuthority(@PathVariable Integer userId,@PathVariable String teamId){
        log.info("团队管理-获取团队里某个用户权限-获取成功");
        return teamService.getUserAuthority(userId,teamId);
    }

    @GetMapping("/{teamId}")
    @Operation(summary = "根据Id获取团队")
    Result<Teams> getTeam(@PathVariable String teamId){
        ArrayList<Teams> teams = new ArrayList<>();
        teams.add(teamService.getById(teamId));
        log.info("团队管理-根据Id获取团队-获取成功");
        return new Result().success(ContentBase.SuccessCode,"获取成功", teams);
    }
}
