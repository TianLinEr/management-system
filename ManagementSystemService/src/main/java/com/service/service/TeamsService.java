package com.service.service;

import com.base.entity.Teams;
import com.baomidou.mybatisplus.extension.service.IService;
import com.base.dto.TeamDTO;
import com.base.vo.TeamVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author psl
 * @since 2024-07-03
 */
public interface TeamsService extends IService<Teams> {

    /**
     * 添加团队（临时团队）
     * @param team
     */
    void addTeam(String team);

    /**
     * 添加团队用户（临时团队）
     * @param teamId
     * @param userId
     */
    void addTeamUserLS(String teamId, String userId);

    /**
     * 获取团队里该用户权限
     * @param userId
     * @param teamId
     * @return
     */
    Integer getUserAuthority(Integer userId, String teamId);

    /**
     * 添加团队用户（正式团队）
     * @param teamVO
     * @param userId
     */
    void addTeamGD(TeamDTO teamVO, String userId);

    /**
     * 删除团队
     * @param teamIds
     */
    void delById(String userId,List<String> teamIds);

    void delById(List<String> teamIds);

    /**
     * 恢复团队
     * @param userId
     * @param teamId
     */
    void revokeById(String userId,String teamId);

    /**
     * 更新团队
     * @param userId
     * @param team
     */
    void updateTeam(String userId, Teams team);

    /**
     * 获取团队列表
     * @param userId
     * @return
     */
    List<TeamVO> getAll(String userId);

    /**
     * 获取与我相关的团队列表
     * @param userId
     * @return
     */
    List<TeamVO> getAllByUserId(String userId);

    List<Integer> test(String userId);

    /**
     * 获取团队根据Id
     * @param teamId
     * @return
     */
    TeamVO selById(String teamId);

    TeamVO getByProjectId(String projectId);
}
