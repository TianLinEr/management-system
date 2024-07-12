package com.base.mapper;

import com.base.vo.TaskVO;
import com.base.entity.Tasks;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

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


    List<TaskVO> selectAll();

    List<TaskVO> selectMy(@Param("userId") String userId);

    List<TaskVO> selectTeam(@Param("teamId") String teamId);

    List<TaskVO> selectProject(@Param("projectId") String projectId);

    void delete(List<Integer> taskIds,String taskState);

    void update(Tasks tasks);
}
