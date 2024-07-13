package com.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.base.content.ContentBase;
import com.base.entity.ProjectTask;
import com.base.entity.Tasks;
import com.base.excepttion.SqlSelectException;
import com.base.excepttion.UserTypeException;
import com.base.mapper.ProjectTaskMapper;
import com.base.mapper.TasksMapper;
import com.base.dto.TaskDTO;
import com.base.vo.ProjectVO;
import com.base.vo.TaskVO;
import com.base.vo.TaskVORes;
import com.base.vo.UserVO;
import com.http.client.ProjectHttp;
import com.http.client.UserHttp;
import com.service.service.TasksService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
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

    private static final String[] taskStatus = {"已完成","进行中","未开始"};

    @Autowired
    private TasksMapper tasksMapper;

    @Autowired
    private ProjectTaskMapper projectTaskMapper;

    @Autowired
    private UserHttp userHttp;

    @Autowired
    private ProjectHttp projectHttp;

    @Override
    @Transactional
    public List<TaskVORes> getAll(String userId) {
        Integer userAuthority = userHttp.getUserAuthority(userId);
        List<TaskVO> task;
        if (Objects.equals(userAuthority, ContentBase.AuthorityToAdmin))
            task = tasksMapper.selectAll();
        else
            task = tasksMapper.selectMy(userId);

        return getTaskVOResList(task);
    }

    private List<TaskVORes> getTaskVOResList(List<TaskVO> task) {
        List<TaskVORes> list = new ArrayList<>();
        if(task==null ||task.isEmpty())
            return list;

        task.forEach(item -> {
            UserVO userPublish = item.getPublishUserId() == null ? null : userHttp.getUserInfo(Collections.singletonList(item.getPublishUserId())).getRes().get(0);
            UserVO userWork = item.getWorkUserId() == null ? null : userHttp.getUserInfo(Collections.singletonList(item.getWorkUserId())).getRes().get(0);

            ProjectVO projectVO=null;
            Set<Integer> set = projectTaskMapper.selectList(
                    new LambdaQueryWrapper<ProjectTask>().eq(ProjectTask::getTaskId, item.getTaskId())
            ).stream().map(ProjectTask::getProjectId).collect(Collectors.toSet());
            if(set.size() == 1){
                Integer next = set.iterator().next();
                projectVO=projectHttp.getById(String.valueOf(next)).getRes().get(0);
            }

            list.add(new TaskVORes(
                    item.getTaskId(),
                    item.getTaskName(),
                    item.getTaskContent(),
                    item.getCreateDate(),
                    item.getExpiryDate(),
                    item.getTaskProgress(),
                    userPublish,
                    userWork,
                    item.getTaskState(),
                    taskStatus[Integer.parseInt(item.getTaskStatus())],
                    item.getTaskType(),
                    item.getTaskFeedback(),
                    projectVO
            ));
        });

        return list;
    }

    @Override
    public List<TaskVORes> getAllTeam(String teamId) {
        List<TaskVO> task = tasksMapper.selectTeam(teamId);

        return getTaskVOResList(task);
    }

    @Override
    public List<TaskVORes> getAllProject(String projectId) {
        List<TaskVO> task = tasksMapper.selectProject(projectId);

        return getTaskVOResList(task);
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
        String taskName = tasks.getTaskName();
        if(taskName==null)
            taskName="task-"+ Instant.now().getEpochSecond();

        Tasks task = new Tasks(null, taskName, tasks.getTaskContent()
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
    public void delTask() {
        List<Integer> taskIds = tasksMapper.selectList(
                        new LambdaQueryWrapper<Tasks>().eq(Tasks::getTaskState
                                , String.valueOf(ContentBase.TaskIsDel)))
                .stream().map(Tasks::getTaskId).collect(Collectors.toList());

        tasksMapper.deleteBatchIds(taskIds);
    }
}
