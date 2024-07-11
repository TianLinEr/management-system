package com.team.controller;

import com.base.content.ContentBase;
import com.base.context.BaseContext;
import com.base.entity.Teams;
import com.base.utils.Result;
import com.base.vo.TeamsVO;
import com.service.service.TeamsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.GetExchange;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        return new Result<>().success(ContentBase.SuccessCode,"添加成功",null);
    }

    @PostMapping("/addUser/{userId}/{teamId}")
    @Operation(summary = "添加团队队员")
    public Result addTeamUserLS(@PathVariable String userId,@PathVariable String teamId){
        log.info("团队管理-添加团队队员-添加成功");
        teamService.addTeamUserLS(teamId,userId);
        return new Result<>().success(ContentBase.SuccessCode,"添加成功",null);
    }

    @PostMapping("/add")
    @Operation(summary = "添加团队与成员")
    public Result addTeam(@RequestBody TeamsVO teamVO){
        String userId = BaseContext.getCurrentId().toString();

        log.info("团队管理-添加团队与成员-添加成功");
        teamService.addTeamGD(teamVO,userId);
        return new Result<>().success(ContentBase.SuccessCode,"添加成功",null);
    }

    @GetMapping("/authority/{userId}/{teamId}")
    @Operation(summary = "获取团队里某个用户权限")
    public Integer getUserAuthority(@PathVariable Integer userId,@PathVariable String teamId){
        log.info("团队管理-获取团队里某个用户权限-获取成功");
        return teamService.getUserAuthority(userId,teamId);
    }

    @GetMapping("/sel/{teamId}")
    @Operation(summary = "根据Id获取团队")
    public Result<Teams> getTeam(@PathVariable String teamId){
        ArrayList<Teams> teams = new ArrayList<>();
        teams.add(teamService.getById(teamId));
        log.info("团队管理-根据Id获取团队-获取成功");
        return new Result<Teams>().success(ContentBase.SuccessCode,"获取成功", teams);
    }

    @DeleteMapping("/del/{teamIds}")
    @Operation(summary = "删除团队")
    public Result delTeam(@PathVariable List<Integer> teamIds){
        String userId = BaseContext.getCurrentId().toString();

        log.info("团队管理-删除团队-删除成功");
        teamService.delById(userId,teamIds);
        return new Result<>().success(ContentBase.SuccessCode,"删除成功",null);
    }

    @DeleteMapping("/del-user/{teamIds}")
    @Operation(summary = "删除团队")
    public Result delTeamTask(@PathVariable List<Integer> teamIds){

        log.info("团队管理-删除团队-删除成功");
        teamService.delById(teamIds);
        return new Result<>().success(ContentBase.SuccessCode,"删除成功",null);
    }

    @PostMapping("/{teamId}")
    @Operation(summary = "恢复团队")
    public Result revoke(@PathVariable String teamId){
        String userId = BaseContext.getCurrentId().toString();

        log.info("团队管理-恢复团队-恢复成功");
        teamService.revokeById(userId,teamId);
        return new Result<>().success(ContentBase.SuccessCode,"恢复成功",null);
    }

    @PutMapping ("/update")
    @Operation(summary = "更新团队")
    public Result updateTeam(@RequestBody Teams team){
        String userId = BaseContext.getCurrentId().toString();

        log.info("团队管理-更新团队-更新成功");
        teamService.updateTeam(userId,team);
        return new Result<>().success(ContentBase.SuccessCode,"更新成功",null);
    }

    @GetMapping("/all")
    @Operation(summary = "获取所有团队")
    public Result<Teams> getAllTeam(){
        String userId = BaseContext.getCurrentId().toString();

        ArrayList<Teams> teams = new ArrayList<>(teamService.getAll(userId));
        log.info("团队管理-获取所有团队-获取成功");
        return new Result<Teams>().success(ContentBase.SuccessCode,"获取成功", teams);
    }

    @GetMapping("/all/{userId}")
    @Operation(summary = "获取所有团队")
    public Result<Teams> getAllTeam(@PathVariable String userId){

        ArrayList<Teams> teams = new ArrayList<>(teamService.getAllByUserId(userId));
        log.info("团队管理-获取所有团队-获取成功");
        return new Result<Teams>().success(ContentBase.SuccessCode,"获取成功", teams);
    }
}
