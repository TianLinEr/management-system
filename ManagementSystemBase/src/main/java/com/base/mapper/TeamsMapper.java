package com.base.mapper;

import com.base.entity.Teams;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author psl
 * @since 2024-07-03
 */
public interface TeamsMapper extends BaseMapper<Teams> {

    @Update("update teams set team_state = #{state} where team_id=#{teamId}")
    void delById(String teamId,String state);

    @Update("update teams set team_name = #{teamName} where team_id=#{teamId}")
    void update(Teams team);

}
