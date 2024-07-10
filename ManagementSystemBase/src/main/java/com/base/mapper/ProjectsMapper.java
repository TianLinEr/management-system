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

    /**
     * 获取所有项目
     * @return
     */
    List<ProjectDTO> getAll();

    /**
     * 获取所有公开项目
     * @param type
     * @param state
     * @return
     */
    List<ProjectDTO> getAllPublic(@Param("project_type") String type,
                @Param("project_state") String state);

    /**
     * 获取我的项目
     * @param id
     * @param type
     * @param state
     * @return
     */
    List<ProjectDTO> getMyProject(@Param("user_id") Integer id,
                                    @Param("project_type") String type,
                                    @Param("project_state") String state);

    /**
     * 删除项目
     * @param id
     * @param state
     */
    @Update("update projects set " +
            "project_state = #{project_state} " +
            "where project_id=#{project_id};")
    void delByProjectId(@Param("project_id") Integer id,
                           @Param("project_state")String state);

    /**
     * 更新项目
     * @param projects
     */
    void updateByProject(@Param("project") Projects projects);
}
