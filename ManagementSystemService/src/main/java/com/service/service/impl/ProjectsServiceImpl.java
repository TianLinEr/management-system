package com.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.content.ContentBase;
import com.base.dto.ProjectDTO;
import com.base.entity.Projects;
import com.base.entity.Teams;
import com.base.excepttion.AddTeamException;
import com.base.excepttion.NoAuthorityUpdateProjectException;
import com.base.excepttion.SqlSelectException;
import com.base.excepttion.UserTypeException;
import com.base.mapper.ProjectsMapper;
import com.base.utils.Result;
import com.http.client.TeamHttp;
import com.http.client.UserHttp;
import com.service.service.ProjectsService;
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
    public List<ProjectDTO> getAll(String id) {
        Integer authority = userHttp.getUserAuthority(id);
        if (authority.equals(ContentBase.AuthorityToUser))
            throw new UserTypeException(ContentBase.ErrorCode);

        return projectsMapper.getAll();
    }

    @Override
    public List<ProjectDTO> getAllPublic() {

        return projectsMapper.getAllPublic(
                String.valueOf(ContentBase.ProjectIsPublic),
                String.valueOf(ContentBase.ProjectNotIsDel));
    }

    @Override
    public List<ProjectDTO> getMyProject(String id) {
        return projectsMapper.getMyProject(Integer.valueOf(id),
                String.valueOf(ContentBase.ProjectIsPublic),
                String.valueOf(ContentBase.ProjectNotIsDel));
    }

    @Override
    @Transactional
    public void delByProjectId(String id,Projects projects) {
        Integer authority = teamHttp.getUserAuthority(Integer.valueOf(id), projects.getTeamId());
        if (authority.equals(ContentBase.AuthorityToDel))
            projectsMapper.delByProjectId(projects.getProjectId(), String.valueOf(ContentBase.ProjectIsDel));
        else
            throw new NoAuthorityUpdateProjectException(ContentBase.ErrorCode);
    }

    @Override
    @Transactional
    public void updateByProject(String id,Projects projects) {
        Integer authority = teamHttp.getUserAuthority(Integer.valueOf(id), projects.getTeamId());
        if (authority.equals(ContentBase.AuthorityToDel))
            projectsMapper.updateByProject(projects);
        else
            throw new NoAuthorityUpdateProjectException(ContentBase.ErrorCode);
    }

    @Override
    @Transactional
    public void revokeProject(String id,Projects projects) {
        Integer authority = teamHttp.getUserAuthority(Integer.valueOf(id), projects.getTeamId());
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
            Result result1 = teamHttp.addTeam(team);
            Result result2 = teamHttp.addTeamUserLS( id,team);
            if(result1.getCode()!=ContentBase.SuccessCode
                && result2.getCode()!=ContentBase.SuccessCode)
                throw new AddTeamException(ContentBase.ErrorCode);
            projects.setTeamId(team);
        }
        projects.setProjectState(String.valueOf(ContentBase.ProjectNotIsDel));
        projects.setProjectCreate(Timestamp.valueOf(time));
        projectsMapper.insert(projects);
    }

    @Override
    @Transactional
    public ProjectDTO getByProjectId(String projectId) {
        Projects projects = this.getById(projectId);
        ProjectDTO projectDTO = new ProjectDTO(
                projects.getProjectId(),
                projects.getProjectName(),
                projects.getProjectContent(),
                null,
                projects.getProjectCreate(),
                projects.getProjectState()
        );
        Result<Teams> team = teamHttp.getTeam(projects.getTeamId());
        if(team.getRes().isEmpty()){
            throw new SqlSelectException(ContentBase.ErrorCode);
        }else {
            projectDTO.setTeam(team.getRes().get(0));
        }
        return projectDTO;
    }
}
