package com.base.mapper;

import com.base.entity.Teams;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
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
public interface TeamsMapper extends BaseMapper<Teams> {


    void delById(List<String> teamIds, String state);

    @Update("update teams set team_name = #{teamName} where team_id=#{teamId}")
    void update(Teams team);

    @Select("select team_id from projects where project_id=#{projectId}")
    Integer getTeamId(String projectId);
}
