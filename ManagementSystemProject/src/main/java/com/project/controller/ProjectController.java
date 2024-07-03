package com.project.controller;

import com.base.dto.ProjectDTO;
import com.base.utils.Result;
import com.project.service.ProjectsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "项目管理")
public class ProjectController {

    @Autowired
    private ProjectsService projectsService;

    @GetMapping("/notLogin/all")
    public Result<ProjectDTO> getAll(){
        List<ProjectDTO> projectDTOList = projectsService.getAllNotLogin();
        return new Result().success(200,"查询成功",projectDTOList);
    }
}
