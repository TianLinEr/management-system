package com.service.service;

import com.base.dto.TaskDTO;
import com.base.entity.Tasks;
import com.baomidou.mybatisplus.extension.service.IService;
import com.base.vo.TaskVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author psl
 * @since 2024-07-03
 */
public interface TasksService extends IService<Tasks> {

    List<TaskDTO> getAll(String userId);

    List<TaskDTO> getAllTeam(String teamId);

    List<TaskDTO> getAllProject(String projectId);

    void delById(String userId,String taskId);

    void insert(TaskVO tasks);

    void revokeById(String userId,String taskId);

    void update(String userId,Tasks tasks);
}
