package com.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.content.ContentBase;
import com.base.dto.ProjectDTO;
import com.base.entity.Projects;
import com.base.excepttion.NoAuthorityUpdateProjectException;
import com.base.excepttion.UserTypeException;
import com.base.mapper.ProjectsMapper;
import com.http.client.TeamHttp;
import com.http.client.UserHttp;
import com.service.service.ProjectsService;
import com.base.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
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

    @Autowired
    private UserHttp userHttp;

    @Autowired
    private TeamHttp teamHttp;

    @Override
    @Transactional
    public List<ProjectDTO> getAll(String id,PageVO pageVO) {
        Integer authority = userHttp.getUserAuthority(id);
        if (authority.equals(ContentBase.AuthorityToUser))
            throw new UserTypeException(ContentBase.ErrorCode);

        return projectsMapper.getAll(pageVO);
    }

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

    @Override
    @Transactional
    public void delByProjectId(String id,Projects projects) {
        Integer authority = projectsMapper.getUserAuthority(Integer.valueOf(id), projects.getTeamId());
        if (authority.equals(ContentBase.AuthorityToDel))
            projectsMapper.delByProjectId(projects.getProjectId(), String.valueOf(ContentBase.ProjectIsDel));
        else
            throw new NoAuthorityUpdateProjectException(ContentBase.ErrorCode);
    }

    @Override
    @Transactional
    public void updateByProject(String id,Projects projects) {
        Integer authority = projectsMapper.getUserAuthority(Integer.valueOf(id), projects.getTeamId());
        if (authority.equals(ContentBase.AuthorityToDel))
            projectsMapper.updateByProject(projects);
        else
            throw new NoAuthorityUpdateProjectException(ContentBase.ErrorCode);
    }

    @Override
    @Transactional
    public void revokeProject(String id,Projects projects) {
        Integer authority = projectsMapper.getUserAuthority(Integer.valueOf(id), projects.getTeamId());
        if (authority.equals(ContentBase.AuthorityToDel))
            projectsMapper.delByProjectId(projects.getProjectId(), String.valueOf(ContentBase.ProjectNotIsDel));
        else
            throw new NoAuthorityUpdateProjectException(ContentBase.ErrorCode);
    }

    @Override
    @Transactional
    public void addProject(String id, Projects projects) {
        String teamId = projects.getTeamId();
        LocalDateTime time=LocalDateTime.now();
        String team="";
        if(teamId==null || teamId==""){
            long timestampSeconds = Instant.now().getEpochSecond();
            team="ls-"+timestampSeconds;
            teamHttp.addTeam(team);
            teamHttp.addTeamUser(team,id);
        }else
            team="gd-"+team;
        projects.setTeamId(team);
        projects.setProjectState(String.valueOf(ContentBase.ProjectNotIsDel));
        projects.setProjectCreate(Timestamp.valueOf(time));
        projectsMapper.insert(projects);
    }
}
