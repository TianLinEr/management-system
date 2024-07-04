package com.base.service;

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

    List<ProjectDTO> getAllPublic(PageVO pageVO);

    List<ProjectDTO> getMyProject(PageVO pageVO,String id);
}
