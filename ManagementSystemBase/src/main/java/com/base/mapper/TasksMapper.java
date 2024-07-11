package com.base.mapper;

import com.base.dto.TaskDTO;
import com.base.entity.Tasks;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author psl
 * @since 2024-07-03
 */
public interface TasksMapper extends BaseMapper<Tasks> {


    List<TaskDTO> selectAll();

    List<TaskDTO> selectMy(@Param("userId") String userId);

    List<TaskDTO> selectTeam(@Param("teamId") String teamId);

    List<TaskDTO> selectProject(@Param("projectId") String projectId);

    void delete(List<Integer> taskIds,String taskState);

    void update(Tasks tasks);
}
