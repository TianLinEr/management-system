package com.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.base.content.ContentBase;
import com.base.entity.TeamUser;
import com.base.entity.Teams;
import com.base.excepttion.AddTeamException;
import com.base.excepttion.NoAuthorityUpdateTeamException;
import com.base.excepttion.SqlSelectException;
import com.base.excepttion.UserTypeException;
import com.base.mapper.TeamUserMapper;
import com.base.mapper.TeamsMapper;
import com.base.vo.TeamsVO;
import com.http.client.UserHttp;
import com.service.service.TeamsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
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

    @Autowired
    private UserHttp userHttp;

    @Override
    public void addTeam(String team) {
        LocalDateTime now = LocalDateTime.now();
        int insert = teamsMapper.insert(new Teams(team, team
                , Timestamp.valueOf(now)
                , String.valueOf(ContentBase.TeamNotIsDel)));
        if (insert == 0)
            throw new AddTeamException(ContentBase.ErrorCode);
    }

    @Override
    public void addTeamUserLS(String teamId, String userId) {
        int insert = teamUserMapper.insert(new TeamUser(null, teamId
                , Integer.valueOf(userId)
                , String.valueOf(ContentBase.AuthorityToAdmin)));
        if (insert == 0)
            throw new AddTeamException(ContentBase.ErrorCode);
    }

    @Override
    public Integer getUserAuthority(Integer userId, String teamId) {
        LambdaQueryWrapper<TeamUser> query = new LambdaQueryWrapper<>();
        query.eq(TeamUser::getUserId, userId);
        query.eq(TeamUser::getTeamId, teamId);

        TeamUser teamUser = teamUserMapper.selectOne(query);
        if (teamUser != null)
            return Integer.valueOf(teamUser.getAuthority());
        else
            throw new SqlSelectException(ContentBase.ErrorCode);
    }

    @Override
    @Transactional
    public void addTeamGD(TeamsVO teamVO,String userId) {
        String teamId = "gd-" + Instant.now().getEpochSecond();
        Teams teams = new Teams(teamId
                , teamVO.getTeamName()
                , Timestamp.valueOf(LocalDateTime.now())
                , String.valueOf(ContentBase.TeamNotIsDel));
        teamsMapper.insert(teams);
        List<TeamUser> list=new ArrayList<>();
        TeamUser teamUser = new TeamUser(null, teams.getTeamId(), Integer.valueOf(userId)
                , String.valueOf(ContentBase.AuthorityToDel));
        list.add(teamUser);
        teamVO.getMemberList().forEach(id -> {
            list.add(new TeamUser(null, teamId, id
                    , String.valueOf(ContentBase.AuthorityNotToDel)));
        });
        teamUserMapper.insert(list);
    }

    @Override
    @Transactional
    public void delById(String userId, String teamId) {
        Integer userAuthority = userHttp.getUserAuthority(userId);
        Integer teamAuthority = teamUserMapper.selectAuthority(userId, teamId);
        if (Objects.equals(userAuthority, ContentBase.AuthorityToAdmin)
                || Objects.equals(teamAuthority, ContentBase.AuthorityToDel)) {
            teamsMapper.delById(teamId,String.valueOf(ContentBase.TeamIsDel));
        }else
            throw new NoAuthorityUpdateTeamException(ContentBase.ErrorCode);
    }

    @Override
    @Transactional
    public void revokeById(String userId, String teamId) {
        Integer userAuthority = userHttp.getUserAuthority(userId);
        Integer teamAuthority = teamUserMapper.selectAuthority(userId, teamId);
        if (Objects.equals(userAuthority, ContentBase.AuthorityToAdmin)
                || Objects.equals(teamAuthority, ContentBase.AuthorityToDel)) {
            teamsMapper.delById(teamId,String.valueOf(ContentBase.TeamNotIsDel));
        }else
            throw new NoAuthorityUpdateTeamException(ContentBase.ErrorCode);
    }

    @Override
    @Transactional
    public void updateTeam(String userId, Teams team) {
        Integer userAuthority = userHttp.getUserAuthority(userId);
        Integer teamAuthority = teamUserMapper.selectAuthority(userId, team.getTeamId());
        if (Objects.equals(userAuthority, ContentBase.AuthorityToAdmin)
                || Objects.equals(teamAuthority, ContentBase.AuthorityToDel)) {
            if(team.getTeamName()!=null)
                teamsMapper.update(team);
        }else
            throw new NoAuthorityUpdateTeamException(ContentBase.ErrorCode);
    }

    @Override
    @Transactional
    public List<Teams> getAll(String userId) {
        Integer userAuthority = userHttp.getUserAuthority(userId);
        if (!Objects.equals(userAuthority, ContentBase.AuthorityToAdmin))
            throw new UserTypeException(ContentBase.ErrorCode);
        return teamsMapper.selectList(new LambdaQueryWrapper<Teams>());
    }

    @Override
    @Transactional
    public List<Teams> getAllByUserId(String userId) {
        List<String> list=teamUserMapper.selectTeamList(userId);
        if(list.size()==0)
            return new ArrayList<>();
        return teamsMapper.selectList(
                new LambdaQueryWrapper<Teams>().in(Teams::getTeamId, list));
    }
}
