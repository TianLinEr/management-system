package com.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.content.ContentBase;
import com.base.dto.ProjectDTO;
import com.base.entity.Projects;
import com.base.mapper.ProjectsMapper;
import com.base.service.ProjectsService;
import com.base.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ProjectsMapper projectsMapper;

    @Override
    public List<ProjectDTO> getAllPublic(PageVO pageVO) {

        return projectsMapper.getAllPublic(pageVO,
                String.valueOf(ContentBase.ProjectIsPublic),
                String.valueOf(ContentBase.ProjectNotIsDel));
    }

    @Override
    public List<ProjectDTO> getMyProject(PageVO pageVO,String id) {
        return projectsMapper.getMyProject(pageVO,
                Integer.valueOf(id),
                String.valueOf(ContentBase.ProjectIsPublic),
                String.valueOf(ContentBase.ProjectNotIsDel));
    }
}
