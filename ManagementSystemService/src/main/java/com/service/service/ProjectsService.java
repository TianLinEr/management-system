package com.service.service;

import com.base.entity.Projects;
import com.baomidou.mybatisplus.extension.service.IService;
import com.base.vo.ProjectVO;

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
    List<ProjectVO> getAll(String id);

    /**
     * 获取所有公开项目
     * @return
     */
    List<ProjectVO> getAllPublic();

    /**
     * 获取我的项目
     * @param id
     * @return
     */
    List<ProjectVO> getMyProject(String id);

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

    /**
     * 获取项目根据项目Id
     *
     * @param projectId
     * @return
     */
    ProjectVO getByProjectId(String projectId);
}
