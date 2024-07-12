package com.project.controller;

import com.base.content.ContentBase;
import com.base.context.BaseContext;
import com.base.dto.ProjectDTO;
import com.base.entity.Projects;
import com.base.utils.Result;
import com.base.vo.ProjectVO;
import com.service.service.ProjectsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.DeleteExchange;

import java.util.ArrayList;
import java.util.List;

@RestController
@Tag(name = "项目管理")
@Slf4j
public class ProjectController {

    @Autowired
    private ProjectsService projectsService;

    @GetMapping("/all")
    @Operation(summary = "获取所有项目")
    public Result<ProjectVO> getAll(){
        String userId = BaseContext.getCurrentId().toString();
        List<ProjectVO> projectDTOList = projectsService.getAll(userId);
        log.info("项目管理-获取所有项目-查询成功");
        return new Result<ProjectVO>().success(ContentBase.SuccessCode,"查询成功",projectDTOList);
    }

    @GetMapping("/all/public")
    @Operation(summary = "获取所有公开项目")
    public Result<ProjectVO> getAllPublic(){
        List<ProjectVO> projectDTOList = projectsService.getAllPublic();
        log.info("项目管理-获取所有公开项目-查询成功");
        return new Result<ProjectVO>().success(ContentBase.SuccessCode,"查询成功",projectDTOList);
    }

    //todo 时区问题
    //todo 项目是否公开改为项目类别

    @GetMapping("/all/my_project")
    @Operation(summary = "获取与我相关的项目")
    public Result<ProjectVO> getMyProject(){
        String userId = BaseContext.getCurrentId().toString();
        List<ProjectVO> projectDTOList = projectsService.getMyProject(userId);
        log.info("项目管理-获取与我相关的项目-查询成功");
        return new Result<ProjectVO>().success(ContentBase.SuccessCode,"查询成功",projectDTOList);
    }

    @GetMapping("/sel/{id}")
    @Operation(summary = "根据id获取项目")
    Result<ProjectVO> getById(@PathVariable String id){
        List<ProjectVO> projectVOList = new ArrayList<>();
        ProjectVO projectVO = projectsService.getByProjectId(id);
        projectVOList.add(projectVO);
        log.info("项目管理-根据id获取项目-查询成功");
        return new Result<ProjectVO>().success(ContentBase.SuccessCode,"查询成功",projectVOList);
    }

    @PutMapping("/update")
    @Operation(summary = "修改项目")
    public Result updateByProject(@RequestBody Projects projects){
        String userId = BaseContext.getCurrentId().toString();

        projectsService.updateByProject(userId,projects);
        log.info("项目管理-修改项目-修改成功");
        return new Result<>().success(ContentBase.SuccessCode,"修改成功",null);
    }

    // todo 删除多个项目
    @DeleteMapping("/del")
    @Operation(summary = "删除项目")
    public Result DelByProjectId(@RequestBody Projects projects){
        String userId = BaseContext.getCurrentId().toString();

        projectsService.delByProjectId(userId,projects);
        log.info("项目管理-删除项目-删除成功");
        return new Result<>().success(ContentBase.SuccessCode,"删除成功",null);
    }

    @PutMapping("/revoke")
    @Operation(summary = "撤销删除项目")
    public Result revokeProject(@RequestBody Projects projects){
        String userId = BaseContext.getCurrentId().toString();

        projectsService.revokeProject(userId,projects);
        log.info("项目管理-撤销删除项目-撤销成功");
        return new Result<>().success(ContentBase.SuccessCode,"撤销成功",null);
    }

    @PostMapping("/add")
    @Operation(summary = "新增项目")
    public Result addProject(@RequestBody Projects projects){
        String userId = BaseContext.getCurrentId().toString();

        projectsService.addProject(userId,projects);
        log.info("项目管理-新增项目-添加成功");
        return new Result<>().success(ContentBase.SuccessCode,"新增成功",null);
    }
}
