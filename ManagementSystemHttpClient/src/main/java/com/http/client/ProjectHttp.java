package com.http.client;

import com.base.dto.ProjectDTO;
import com.base.entity.Projects;
import com.base.utils.Result;
import com.base.vo.PageVO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.*;


@HttpExchange("/project")
public interface ProjectHttp {

    /**
     * 获取所有项目
     * @param pageVO
     * @return
     */
    @GetExchange("/all")
    Result<ProjectDTO> getAll(PageVO pageVO);

    /**
     * 获取所有公开项目
     * @param pageVO
     * @return
     */
    @GetExchange("/all/public")
    Result<ProjectDTO> getAllPublic(PageVO pageVO);


    /**
     * 获取我的项目
     * @param pageVO
     * @return
     */
    @GetExchange("/all/my_project")
    Result<ProjectDTO> getMyProject(PageVO pageVO);

    /**
     * 修改项目
     * @param projects
     * @return
     */
    @PutExchange("/update")
    Result updateByProject(@RequestBody Projects projects);

    /**
     * 删除项目
     * @param projects
     * @return
     */
    @DeleteExchange("/del")
    Result DelByProjectId(@RequestBody Projects projects);

    /**
     * 撤销删除项目
     * @param projects
     * @return
     */
    @PutExchange("/revoke")
    Result revokeProject(@RequestBody Projects projects);

    /**
     * 添加项目
     * @param projects
     * @return
     */
    @PostExchange("/add")
    Result addProject(@RequestBody Projects projects);
}
