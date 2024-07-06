package com.service.service;

import com.base.dto.ProjectDTO;
import com.base.entity.Projects;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author psl
 * @since 2024-07-03
 */
public interface ProjectsService extends IService<Projects> {

    /**
     * 获取所有项目
     * @param id
     * @return
     */
    List<ProjectDTO> getAll(String id);

    /**
     * 获取所有公开项目
     * @return
     */
    List<ProjectDTO> getAllPublic();

    /**
     * 获取我的项目
     * @param id
     * @return
     */
    List<ProjectDTO> getMyProject(String id);

    /**
     * 根据项目Id删除项目
     * @param id
     * @param projects
     */
    void delByProjectId(String id,Projects projects);

    /**
     * 更新项目
     * @param id
     * @param projects
     */
    void updateByProject(String id,Projects projects);

    /**
     * 撤回删除项目
     * @param id
     * @param projects
     */
    void revokeProject(String id,Projects projects);

    /**
     * 添加项目
     * @param id
     * @param projects
     */
    void addProject(String id,Projects projects);

    ProjectDTO getByProjectId(String id);
}
