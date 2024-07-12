package com.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.content.ContentBase;
import com.base.dto.ProjectDTO;
import com.base.entity.ProjectDocument;
import com.base.entity.Projects;
import com.base.excepttion.AddTeamException;
import com.base.excepttion.NoAuthorityUpdateProjectException;
import com.base.excepttion.SqlSelectException;
import com.base.excepttion.UserTypeException;
import com.base.mapper.ProjectDocumentMapper;
import com.base.mapper.ProjectTaskMapper;
import com.base.mapper.ProjectsMapper;
import com.base.utils.Result;
import com.base.vo.ProjectVO;
import com.base.vo.TeamVO;
import com.http.client.DocumentHttp;
import com.http.client.TaskHttp;
import com.http.client.TeamHttp;
import com.http.client.UserHttp;
import com.service.service.ProjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
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
    private ProjectDocumentMapper projectDocumentMapper;

    @Autowired
    private ProjectTaskMapper projectTaskMapper;

    @Autowired
    private DocumentHttp documentHttp;

    @Autowired
    private TaskHttp taskHttp;

    @Autowired
    private UserHttp userHttp;

    @Autowired
    private TeamHttp teamHttp;

    @Override
    @Transactional
    public List<ProjectVO> getAll(String id) {
        Integer authority = userHttp.getUserAuthority(id);
        if (authority.equals(ContentBase.AuthorityToUser))
            throw new UserTypeException(ContentBase.ErrorCode);

        List<ProjectDTO> list = projectsMapper.getAll();
        List<ProjectVO> res = new ArrayList<>();

        if (!list.isEmpty()) {
            list.forEach(item -> {
                        res.add(new ProjectVO(
                                item.getProjectId(),
                                item.getProjectName(),
                                item.getProjectContent(),
                                teamHttp.getTeam(item.getTeam().getTeamId()).getRes().get(0),
                                item.getCreateDate().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                                item.getProjectState()
                        ));
                    }
            );
        }

        return res;
    }

    @Override
    public List<ProjectVO> getAllPublic() {
        List<ProjectDTO> list = projectsMapper.getAllPublic(
                String.valueOf(ContentBase.ProjectNotIsDel));
        if (list.isEmpty())
            return new ArrayList<>();

        List<ProjectVO> res = new ArrayList<>();
        list.forEach(item -> {
                    res.add(new ProjectVO(
                            item.getProjectId(),
                            item.getProjectName(),
                            item.getProjectContent(),
                            teamHttp.getTeam(item.getTeam().getTeamId()).getRes().get(0),
                            item.getCreateDate().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                            item.getProjectState()
                    ));
                }
       );
        return res;
    }

    @Override
    public List<ProjectVO> getMyProject(String id) {
        List<ProjectDTO> project = projectsMapper.getMyProject(Integer.valueOf(id),
                String.valueOf(ContentBase.ProjectNotIsDel));

        if (project.isEmpty())
            return new ArrayList<>();

        List<ProjectVO> res = new ArrayList<>();
        project.forEach(item -> {
                    res.add(new ProjectVO(
                            item.getProjectId(),
                            item.getProjectName(),
                            item.getProjectContent(),
                            teamHttp.getTeam(item.getTeam().getTeamId()).getRes().get(0),
                            item.getCreateDate().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                            item.getProjectState()
                    ));
                }
        );
        return res;
    }

    @Override
    @Transactional
    public void delByProjectId(String id, Projects projects) {
        Integer authority = teamHttp.getUserAuthority(Integer.valueOf(id), projects.getTeamId());
        Integer userAuthority = userHttp.getUserAuthority(id);
        if (authority.equals(ContentBase.AuthorityToDel)
                || userAuthority.equals(ContentBase.AuthorityToAdmin))
            projectsMapper.delByProjectId(projects.getProjectId(), String.valueOf(ContentBase.ProjectIsDel));
        else
            throw new NoAuthorityUpdateProjectException(ContentBase.ErrorCode);
    }

    @Override
    @Transactional
    public void updateByProject(String id, Projects projects) {
        Integer authority = teamHttp.getUserAuthority(Integer.valueOf(id), projects.getTeamId());
        Integer userAuthority = userHttp.getUserAuthority(id);
        if (authority.equals(ContentBase.AuthorityToDel)
                || userAuthority.equals(ContentBase.AuthorityToAdmin))
            projectsMapper.updateByProject(projects);
        else
            throw new NoAuthorityUpdateProjectException(ContentBase.ErrorCode);
    }

    @Override
    @Transactional
    public void revokeProject(String id, Projects projects) {
        Integer authority = teamHttp.getUserAuthority(Integer.valueOf(id), projects.getTeamId());
        Integer userAuthority = userHttp.getUserAuthority(id);
        if (authority.equals(ContentBase.AuthorityToDel)
                || userAuthority.equals(ContentBase.AuthorityToAdmin))
            projectsMapper.delByProjectId(projects.getProjectId(), String.valueOf(ContentBase.ProjectNotIsDel));
        else
            throw new NoAuthorityUpdateProjectException(ContentBase.ErrorCode);
    }

    @Override
    @Transactional
    public void addProject(String id, Projects projects) {
        String teamId = projects.getTeamId();
        LocalDateTime time = LocalDateTime.now();
        String team = "";
        if (teamId == null || teamId == "") {
            long timestampSeconds = Instant.now().getEpochSecond();
            team = "ls-" + timestampSeconds;
            Result result1 = teamHttp.addTeam(team);
            Result result2 = teamHttp.addTeamUserLS(id, team);
            if (result1.getCode() != ContentBase.SuccessCode
                    && result2.getCode() != ContentBase.SuccessCode)
                throw new AddTeamException(ContentBase.ErrorCode);
            projects.setTeamId(team);
        }
        projects.setProjectState(String.valueOf(ContentBase.ProjectNotIsDel));
        projects.setProjectCreate(Timestamp.valueOf(time));
        projectsMapper.insert(projects);
    }

    @Override
    @Transactional
    public ProjectVO getByProjectId(String projectId) {
        Projects projects = this.getById(projectId);
        if (projects == null)
            return null;

        ProjectVO projectVO = new ProjectVO(
                projects.getProjectId(),
                projects.getProjectName(),
                projects.getProjectContent(),
                null,
                projects.getProjectCreate().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                projects.getProjectState()
        );
        Result<TeamVO> team = teamHttp.getTeam(projects.getTeamId());
        if (team.getRes().isEmpty()) {
            throw new SqlSelectException(ContentBase.ErrorCode);
        } else {
            projectVO.setTeam(team.getRes().get(0));
        }
        return projectVO;
    }

    @Scheduled(cron = "* 30 23 * * ?")
    @Transactional
    public void delProject() {
        List<Projects> projects = projectsMapper.selectList(
                new LambdaQueryWrapper<Projects>().eq(Projects::getProjectState, String.valueOf(ContentBase.ProjectIsDel))
        );
        List<Integer> list = projects.stream().map(Projects::getProjectId).collect(Collectors.toList());
        if (!list.isEmpty()) {
            List<Integer> documentIds = projectDocumentMapper.selectList(
                    new LambdaQueryWrapper<ProjectDocument>().in(ProjectDocument::getProjectId, list)
            ).stream().map(ProjectDocument::getDocumentId).collect(Collectors.toList());

            documentHttp.deleteById(documentIds);
            projectsMapper.deleteBatchIds(list);
            documentHttp.deleteProjectIds(list);
            taskHttp.deleteProjectIds(list);
        }

    }
}
