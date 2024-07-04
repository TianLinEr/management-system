package com.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.base.dto.ProjectDTO;
import com.base.entity.Projects;
import com.base.vo.PageVO;
import org.apache.ibatis.annotations.Mapper;
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
@Mapper
public interface ProjectsMapper extends BaseMapper<Projects> {

    List<ProjectDTO> getAllPublic(@Param("page_vo") PageVO pageVO,
                                  @Param("project_type") String type,
                                  @Param("project_state") String state);

    List<ProjectDTO> getMyProject(@Param("page_vo") PageVO pageVO,
                                    @Param("user_id") Integer id,
                                    @Param("project_type") String type,
                                    @Param("project_state") String state);
}
