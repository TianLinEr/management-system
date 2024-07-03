package com.project.service.impl;

import com.base.dto.ProjectDTO;
import com.base.entity.Projects;
import com.project.mapper.ProjectsMapper;
import com.project.service.ProjectsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author psl
 * @since 2024-07-03
 */
@Service
public class ProjectsServiceImpl extends ServiceImpl<ProjectsMapper, Projects> implements ProjectsService {

    //todo: 未完成

    @Override
    public List<ProjectDTO> getAllNotLogin() {
        return null;
    }
}
