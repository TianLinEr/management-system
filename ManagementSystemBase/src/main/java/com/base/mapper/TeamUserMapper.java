package com.base.mapper;

import com.base.entity.TeamUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author psl
 * @since 2024-07-03
 */
public interface TeamUserMapper extends BaseMapper<TeamUser> {

    int insert(List<TeamUser> list);

    @Select("select authority from team_user where user_id = #{userId} and team_id=#{teamId}")
    Integer selectAuthority(String userId, String teamId);

    @Select("select team_id from team_user where user_id = #{userId}")
    List<String> selectTeamList(String userId);
}
