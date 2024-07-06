package com.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.base.content.ContentBase;
import com.base.entity.TeamUser;
import com.base.entity.Teams;
import com.base.excepttion.AddTeamException;
import com.base.excepttion.SqlSelectException;
import com.base.mapper.TeamUserMapper;
import com.base.mapper.TeamsMapper;
import com.service.service.TeamsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author psl
 * @since 2024-07-03
 */
@Service
public class TeamsServiceImpl extends ServiceImpl<TeamsMapper, Teams> implements TeamsService {

    @Autowired
    private TeamsMapper teamsMapper;

    @Autowired
    private TeamUserMapper teamUserMapper;

    /**
     * 添加团队(隐式)
     * @param team
     */
    @Override
    public void addTeam(String team) {
        LocalDateTime now = LocalDateTime.now();
        int insert = teamsMapper.insert(new Teams(team, team
                , Timestamp.valueOf(now)
                , String.valueOf(ContentBase.TeamNotIsDel)));
        if (insert == 0)
            throw new AddTeamException(ContentBase.ErrorCode);
    }

    /**
     * 添加团队某个用户权限
     * @param teamId
     * @param id
     */
    @Override
    public void addTeamUserLS(String teamId, String id) {
        int insert = teamUserMapper.insert(new TeamUser(null,teamId
                ,Integer.valueOf(id)
                ,String.valueOf(ContentBase.AuthorityToAdmin)));
        if (insert == 0)
            throw new AddTeamException(ContentBase.ErrorCode);
    }

    /**
     * 获取团队用户权限
     * @param userId
     * @param teamId
     * @return
     */
    @Override
    public Integer getUserAuthority(Integer userId, String teamId) {
        LambdaQueryWrapper<TeamUser> query = new LambdaQueryWrapper<>();
        query.eq(TeamUser::getUserId,userId);
        query.eq(TeamUser::getTeamId,teamId);

        TeamUser teamUser = teamUserMapper.selectOne(query);
        if (teamUser != null)
            return Integer.valueOf(teamUser.getAuthority());
        else
            throw new SqlSelectException(ContentBase.ErrorCode);
    }
}
