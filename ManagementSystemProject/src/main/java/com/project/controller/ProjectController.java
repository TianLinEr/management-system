package com.project.controller;

import com.base.content.ContentBase;
import com.base.dto.ProjectDTO;
import com.base.utils.Result;
import com.base.service.ProjectsService;
import com.base.vo.PageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

@RestController
@Tag(name = "项目管理")
@Slf4j
public class ProjectController {

    @Autowired
    private ProjectsService projectsService;

    @GetExchange("/all/public")
    @Operation(summary = "获取所有公开项目")
    public Result<ProjectDTO> getAllPublic(PageVO pageVO){
        List<ProjectDTO> projectDTOList = projectsService.getAllPublic(pageVO);
        log.info("项目管理-获取所有公开项目-查询成功");
        return new Result().success(ContentBase.SuccessCode,"查询成功",projectDTOList);
    }

    //todo 时区问题

    @GetExchange("/all/my_project")
    @Operation(summary = "获取与我相关的项目")
    public Result<ProjectDTO> getMyProject(PageVO pageVO){
        String id="1";
        // todo 未完成
        List<ProjectDTO> projectDTOList = projectsService.getMyProject(pageVO,id);
        log.info("项目管理-获取与我相关的项目-查询成功");
        return new Result().success(ContentBase.SuccessCode,"查询成功",projectDTOList);
    }
}
