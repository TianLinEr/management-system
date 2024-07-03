package com.project.service;

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

    List<ProjectDTO> getAllNotLogin();
}
