package com.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.base.content.ContentBase;
import com.base.entity.ProjectTask;
import com.base.entity.Tasks;
import com.base.excepttion.UserTypeException;
import com.base.mapper.ProjectTaskMapper;
import com.base.mapper.TasksMapper;
import com.base.dto.TaskDTO;
import com.base.vo.TaskVO;
import com.http.client.UserHttp;
import com.service.service.TasksService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
public class TasksServiceImpl extends ServiceImpl<TasksMapper, Tasks> implements TasksService {

    @Autowired
    private TasksMapper tasksMapper;

    @Autowired
    private ProjectTaskMapper projectTaskMapper;

    @Autowired
    private UserHttp userHttp;

    @Override
    @Transactional
    public List<TaskVO> getAll(String userId) {
        List<TaskVO> task;
        if (Objects.equals(userId, String.valueOf(ContentBase.AuthorityToAdmin)))
            task = tasksMapper.selectAll();
        else
            task = tasksMapper.selectMy(userId);
        return task;
    }

    @Override
    public List<TaskVO> getAllTeam(String teamId) {

        return tasksMapper.selectTeam(teamId);
    }

    @Override
    public List<TaskVO> getAllProject(String projectId) {
        return tasksMapper.selectProject(projectId);
    }

    @Override
    @Transactional
    public void delById(String userId, List<Integer> taskIds) {
        Integer userAuthority = userHttp.getUserAuthority(userId);
        List<Tasks> tasks = tasksMapper.selectList(
                new LambdaQueryWrapper<Tasks>().in(Tasks::getTaskId, taskIds));
        tasks.forEach(item -> {
            if (!Objects.equals(userAuthority, ContentBase.AuthorityToAdmin)
                    || item.getPublishUserId() != Integer.parseInt(userId))
                throw new UserTypeException(ContentBase.ErrorCode);
        });
        tasksMapper.delete(taskIds, String.valueOf(ContentBase.TaskIsDel));

    }

    @Override
    @Transactional
    public void insert(TaskDTO tasks) {
        Tasks task = new Tasks(null, tasks.getTaskName(), tasks.getTaskContent()
                , Timestamp.valueOf(LocalDateTime.now())
                , Timestamp.valueOf(LocalDateTime.now().plusDays(tasks.getDays()))
                , null, tasks.getPublishUserId(), tasks.getWorkUserId()
                , String.valueOf(ContentBase.TaskNotIsDel), null);
        tasksMapper.insert(task);
        projectTaskMapper.insert(new ProjectTask(
                null, tasks.getProjectId(), task.getTaskId()
                , ContentBase.TaskNew, String.valueOf(ContentBase.TaskIsPublic)));
    }

    @Override
    @Transactional
    public void revokeById(String userId, String taskId) {
        Integer userAuthority = userHttp.getUserAuthority(userId);
        Tasks tasks = tasksMapper.selectById(taskId);
        if (Objects.equals(userAuthority, ContentBase.AuthorityToAdmin)
                || tasks.getPublishUserId() == Integer.parseInt(userId))
            throw new UserTypeException(ContentBase.ErrorCode);

        List<Integer> taskIds = new ArrayList<>();
        taskIds.add(Integer.parseInt(taskId));
        tasksMapper.delete(taskIds, String.valueOf(ContentBase.TaskNotIsDel));
    }

    @Override
    public void update(String userId, Tasks tasks) {
        Integer userAuthority = userHttp.getUserAuthority(userId);
        if (Objects.equals(userAuthority, ContentBase.AuthorityToAdmin)) {
            if (tasks.getPublishUserId() == Integer.parseInt(userId))
                tasksMapper.update(tasks);
            else
                throw new UserTypeException(ContentBase.ErrorCode);
        } else
            throw new UserTypeException(ContentBase.ErrorCode);

    }

    @Override
    @Transactional
    public void delProjectIds(List<Integer> list) {
        List<Integer> taskIds = projectTaskMapper.selectList(
                new LambdaQueryWrapper<ProjectTask>().in(ProjectTask::getProjectId, list)
        ).stream().map(ProjectTask::getTaskId).collect(Collectors.toList());
        tasksMapper.delete(taskIds, String.valueOf(ContentBase.TaskIsDel));
    }

    @Transactional
    @Scheduled(cron = "* 50 23 * * ?")
    public void delTask(){
        List<Integer> taskIds = tasksMapper.selectList(
                        new LambdaQueryWrapper<Tasks>().eq(Tasks::getTaskState
                                , String.valueOf(ContentBase.TaskIsDel)))
                .stream().map(Tasks::getTaskId).collect(Collectors.toList());

        tasksMapper.deleteBatchIds(taskIds);
    }
}
