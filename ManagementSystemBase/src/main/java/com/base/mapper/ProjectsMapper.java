package com.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.base.dto.ProjectDTO;
import com.base.entity.Projects;
import com.base.vo.PageVO;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
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

    List<ProjectDTO> getAll(@Param("page_vo") PageVO pageVO);

    List<ProjectDTO> getAllPublic(@Param("page_vo") PageVO pageVO,
                                  @Param("project_type") String type,
                                  @Param("project_state") String state);

    List<ProjectDTO> getMyProject(@Param("page_vo") PageVO pageVO,
                                    @Param("user_id") Integer id,
                                    @Param("project_type") String type,
                                    @Param("project_state") String state);

    @Select("select authority from team_user where user_id=#{user_id} and team_id=#{team_id} ;")
    Integer getUserAuthority(@Param("user_id") Integer id,
                             @Param("team_id") String teamId);

    @Update("update projects set " +
            "project_state = #{project_state} " +
            "where project_id=#{project_id};")
    void delByProjectId(@Param("project_id") Integer id,
                           @Param("project_state")String state);

    void updateByProject(@Param("project") Projects projects);
}
