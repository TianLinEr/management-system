package com.service.service;

import com.base.dto.ProjectDTO;
import com.base.entity.Projects;
import com.baomidou.mybatisplus.extension.service.IService;
import com.base.vo.PageVO;

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

    List<ProjectDTO> getAll(String id,PageVO pageVO);

    List<ProjectDTO> getAllPublic(PageVO pageVO);

    List<ProjectDTO> getMyProject(PageVO pageVO,String id);

    void delByProjectId(String id,Projects projects);

    void updateByProject(String id,Projects projects);

    void revokeProject(String id,Projects projects);

    void addProject(String id,Projects projects);
}
