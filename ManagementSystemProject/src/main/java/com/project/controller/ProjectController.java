package com.project.controller;

import com.base.content.ContentBase;
import com.base.dto.ProjectDTO;
import com.base.entity.Projects;
import com.base.utils.Result;
import com.base.vo.PageVO;
import com.service.service.ProjectsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "项目管理")
@Slf4j
public class ProjectController {

    @Autowired
    private ProjectsService projectsService;

    @GetMapping("/all")
    @Operation(summary = "获取所有项目")
    public Result<ProjectDTO> getAll(PageVO pageVO){
        String id="1";
        // todo 未完成
        List<ProjectDTO> projectDTOList = projectsService.getAll(id,pageVO);
        log.info("项目管理-获取所有项目-查询成功");
        return new Result().success(ContentBase.SuccessCode,"查询成功",projectDTOList);
    }

    @GetMapping("/all/public")
    @Operation(summary = "获取所有公开项目")
    public Result<ProjectDTO> getAllPublic(PageVO pageVO){
        List<ProjectDTO> projectDTOList = projectsService.getAllPublic(pageVO);
        log.info("项目管理-获取所有公开项目-查询成功");
        return new Result().success(ContentBase.SuccessCode,"查询成功",projectDTOList);
    }

    //todo 时区问题

    @GetMapping("/all/my_project")
    @Operation(summary = "获取与我相关的项目")
    public Result<ProjectDTO> getMyProject(PageVO pageVO){
        String id="1";
        // todo 未完成
        List<ProjectDTO> projectDTOList = projectsService.getMyProject(pageVO,id);
        log.info("项目管理-获取与我相关的项目-查询成功");
        return new Result().success(ContentBase.SuccessCode,"查询成功",projectDTOList);
    }

    @PutMapping("/update")
    @Operation(summary = "修改项目")
    public Result updateByProject(@RequestBody Projects projects){
        String id="1";
        // todo 未完成
        projectsService.updateByProject(id,projects);
        log.info("项目管理-修改项目-修改成功");
        return new Result<>().success(ContentBase.SuccessCode,"修改成功",null);
    }

    @DeleteMapping("/del")
    @Operation(summary = "删除项目")
    public Result DelByProjectId(@RequestBody Projects projects){
        String id="1";
        // todo 未完成
        projectsService.delByProjectId(id,projects);
        log.info("项目管理-删除项目-删除成功");
        return new Result<>().success(ContentBase.SuccessCode,"删除成功",null);
    }

    @PutMapping("/revoke")
    @Operation(summary = "撤销删除项目")
    public Result revokeProject(@RequestBody Projects projects){
        String id="1";
        // todo 未完成
        projectsService.revokeProject(id,projects);
        log.info("项目管理-撤销删除项目-撤销成功");
        return new Result<>().success(ContentBase.SuccessCode,"撤销成功",null);
    }

    @PostMapping("/add")
    @Operation(summary = "新增项目")
    public Result addProject(@RequestBody Projects projects){
        String id="1";
        projectsService.addProject(id,projects);
        log.info("项目管理-新增项目-添加成功");
        return new Result<>().success(ContentBase.SuccessCode,"新增成功",null);
    }
}
