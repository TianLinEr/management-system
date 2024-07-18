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
import com.base.dto.TeamDTO;
import com.base.utils.Result;
import com.base.vo.TeamVO;
import com.base.vo.UserVO;
import com.http.client.UserHttp;
import com.service.service.TeamsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        List<TeamUser> list = new ArrayList<>();
        list.add(new TeamUser(null, teamId
                , Integer.valueOf(userId)
                , String.valueOf(ContentBase.AuthorityToAdmin)));
        int insert = teamUserMapper.insert(list);
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
    public void addTeamGD(TeamDTO teamVO, String userId) {
        String teamId = "gd-" + Instant.now().getEpochSecond();
        Teams teams = new Teams(teamId
                , teamVO.getTeamName()
                , Timestamp.valueOf(LocalDateTime.now())
                , String.valueOf(ContentBase.TeamNotIsDel));
        teamsMapper.insert(teams);
        List<TeamUser> list = new ArrayList<>();
        TeamUser teamUser = new TeamUser(null, teams.getTeamId(), Integer.valueOf(userId)
                , String.valueOf(ContentBase.AuthorityToDel));
        list.add(teamUser);
        teamVO.getMemberList().forEach(id -> {
            if (!Objects.equals(id, Integer.valueOf(userId)))
                list.add(new TeamUser(null, teamId, id
                        , String.valueOf(ContentBase.AuthorityNotToDel)));
        });
        teamUserMapper.insert(list);
    }

    @Override
    @Transactional
    public void delById(String userId, List<String> teamIds) {
        Integer userAuthority = userHttp.getUserAuthority(userId);
        if (!Objects.equals(userAuthority, ContentBase.AuthorityToAdmin))
            throw new NoAuthorityUpdateTeamException(ContentBase.ErrorCode);

        teamsMapper.delById(teamIds, String.valueOf(ContentBase.TeamIsDel));
    }

    @Override
    @Transactional
    public void delById(List<String> teamIds) {

        teamsMapper.delById(teamIds, String.valueOf(ContentBase.TeamIsDel));
    }

    @Override
    @Transactional
    public void revokeById(String userId, String teamId) {
        Integer userAuthority = userHttp.getUserAuthority(userId);
        if (Objects.equals(userAuthority, ContentBase.AuthorityToAdmin))
            throw new NoAuthorityUpdateTeamException(ContentBase.ErrorCode);

        List<String> list = new ArrayList<>();
        list.add(userId);
        teamsMapper.delById(list, String.valueOf(ContentBase.TeamNotIsDel));
    }

    @Override
    @Transactional
    public void updateTeam(String userId, Teams team) {
        Integer userAuthority = userHttp.getUserAuthority(userId);
        Integer teamAuthority = teamUserMapper.selectAuthority(userId, team.getTeamId());
        if (Objects.equals(userAuthority, ContentBase.AuthorityToAdmin)
                || Objects.equals(teamAuthority, ContentBase.AuthorityToDel)) {
            if (team.getTeamName() != null)
                teamsMapper.update(team);
        } else
            throw new NoAuthorityUpdateTeamException(ContentBase.ErrorCode);
    }

    @Override
    @Transactional
    public List<TeamVO> getAll(String userId) {
        Integer userAuthority = userHttp.getUserAuthority(userId);
        if (!Objects.equals(userAuthority, ContentBase.AuthorityToAdmin))
            throw new UserTypeException(ContentBase.ErrorCode);

        List<Teams> teamsList = teamsMapper.selectList(new LambdaQueryWrapper<Teams>());
        List<TeamVO> teamVOList = new ArrayList<>();

        teamsList.forEach(teams -> {
            List<TeamUser> teamUsers = teamUserMapper.selectList(new LambdaQueryWrapper<TeamUser>().eq(TeamUser::getTeamId, teams.getTeamId()));
            List<Integer> list = teamUsers.stream().map(TeamUser::getUserId).collect(Collectors.toList());
            TeamVO teamVO = new TeamVO();
            if (list.size() != 0) {
                List<UserVO> userVO = userHttp.getUserInfo(list).getRes();
                userVO.forEach(item -> {
                    item.setUserCategory(teamUserMapper.selectOne(
                            new LambdaQueryWrapper<TeamUser>()
                                    .eq(TeamUser::getUserId, item.getUserId())
                                    .eq(TeamUser::getTeamId, teams.getTeamId())
                    ).getAuthority());
                });
                teamVO = new TeamVO(
                        teams.getTeamId(),
                        teams.getTeamName(),
                        teams.getCreateDate().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        teams.getTeamState(),
                        userVO
                );
            }
            teamVOList.add(teamVO);
        });

        return teamVOList;
    }

    @Override
    @Transactional
    public List<TeamVO> getAllByUserId(String userId) {
        List<String> teamIds = teamUserMapper.selectTeamList(userId);
        if (teamIds.size() == 0)
            return new ArrayList<>();

        List<Teams> teamsList = teamsMapper.selectList(
                new LambdaQueryWrapper<Teams>().in(Teams::getTeamId, teamIds));
        List<TeamVO> teamVOList = new ArrayList<>();
        teamsList.forEach(teams -> {
            List<TeamUser> teamUsers = teamUserMapper.selectList(new LambdaQueryWrapper<TeamUser>().eq(TeamUser::getTeamId, teams.getTeamId()));
            List<Integer> list = teamUsers.stream().map(TeamUser::getUserId).collect(Collectors.toList());
            TeamVO teamVO = null;
            if (list.size() != 0) {
                List<UserVO> userVO = userHttp.getUserInfo(list).getRes();
                userVO.forEach(item -> {
                    item.setUserCategory(teamUserMapper.selectOne(
                            new LambdaQueryWrapper<TeamUser>()
                                    .eq(TeamUser::getUserId, item.getUserId())
                                    .eq(TeamUser::getTeamId, teams.getTeamId())
                    ).getAuthority());
                });
                teamVO = new TeamVO(
                        teams.getTeamId(),
                        teams.getTeamName(),
                        teams.getCreateDate().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        teams.getTeamState(),
                        userVO
                );

            }
            teamVOList.add(teamVO);
        });
        return teamVOList;
    }

    @Override
    @Transactional
    public List<Integer> test(String userId) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Integer userAuthority = userHttp.getUserAuthority(userId);
            list.add(userAuthority + i);
        }
        Result<UserVO> userInfo = userHttp.getUserInfo(list);
        System.out.println(userInfo.getRes().toString());

        return list;
    }

    @Override
    @Transactional
    public TeamVO selById(String teamId) {
        Teams teams = teamsMapper.selectById(teamId);
        if (teams == null)
            return null;

        List<Integer> list = teamUserMapper.selectList(
                new LambdaQueryWrapper<TeamUser>().eq(TeamUser::getTeamId, teamId)
        ).stream().map(TeamUser::getUserId).collect(Collectors.toList());

        List<UserVO> userVO = null;
        if (list.size() != 0) {
            userVO = userHttp.getUserInfo(list).getRes();
            userVO.forEach(item -> {
                item.setUserCategory(teamUserMapper.selectOne(
                        new LambdaQueryWrapper<TeamUser>()
                                .eq(TeamUser::getUserId, item.getUserId())
                                .eq(TeamUser::getTeamId, teamId)
                ).getAuthority());
            });
        }

        return new TeamVO(
                teams.getTeamId(),
                teams.getTeamName(),
                teams.getCreateDate().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                teams.getTeamState(),
                userVO
        );
    }

    @Override
    public TeamVO getByProjectId(String projectId) {
        Integer teamId = teamsMapper.getTeamId(projectId);
        List<TeamUser> list = teamUserMapper.selectList(new LambdaQueryWrapper<TeamUser>().eq(TeamUser::getTeamId, teamId));
        Teams teams = teamsMapper.selectById(teamId);
        return new TeamVO(
                teams.getTeamId(),
                teams.getTeamName(),
                teams.getCreateDate().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                teams.getTeamState(),
                userHttp.getUserInfo(list.stream().map(TeamUser::getUserId).collect(Collectors.toList())).getRes()
        );
    }

    @Transactional
    @Scheduled(cron = "* 20 0 * * ?")
    public void delTeam() {
        List<String> teamIds = teamsMapper.selectList(
                new LambdaQueryWrapper<Teams>().eq(Teams::getTeamState
                        , String.valueOf(ContentBase.TeamIsDel))
        ).stream().map(Teams::getTeamId).collect(Collectors.toList());

        teamsMapper.deleteBatchIds(teamIds);
        teamUserMapper.delete(
                new LambdaQueryWrapper<TeamUser>().in(TeamUser::getTeamId, teamIds)
        );
    }
}
