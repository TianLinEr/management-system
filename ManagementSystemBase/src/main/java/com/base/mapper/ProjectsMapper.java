package com.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.base.dto.ProjectDTO;
import com.base.entity.Projects;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author psl
 * @since 2024-07-03
 */
@Mapper
public interface ProjectsMapper extends BaseMapper<Projects> {

    List<ProjectDTO> getAll();

    List<ProjectDTO> getAllPublic(@Param("project_type") String type,
                @Param("project_state") String state);

    List<ProjectDTO> getMyProject(@Param("user_id") Integer id,
                                    @Param("project_type") String type,
                                    @Param("project_state") String state);

    @Update("update projects set " +
            "project_state = #{project_state} " +
            "where project_id=#{project_id};")
    void delByProjectId(@Param("project_id") Integer id,
                           @Param("project_state")String state);

    void updateByProject(@Param("project") Projects projects);
}
