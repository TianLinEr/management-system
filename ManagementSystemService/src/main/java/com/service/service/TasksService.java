package com.service.service;

import com.base.entity.Tasks;
import com.baomidou.mybatisplus.extension.service.IService;
import com.base.dto.TaskDTO;
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

    List<TaskVO> getAll(String userId);

    List<TaskVO> getAllTeam(String teamId);

    List<TaskVO> getAllProject(String projectId);

    void delById(String userId,List<Integer> taskIds);

    void insert(TaskDTO tasks);

    void revokeById(String userId,String taskId);

    void update(String userId,Tasks tasks);

    void delProjectIds(List<Integer> list);
}
