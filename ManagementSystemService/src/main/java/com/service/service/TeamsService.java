package com.service.service;

import com.base.entity.Teams;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author psl
 * @since 2024-07-03
 */
public interface TeamsService extends IService<Teams> {

    void addTeam(String team);

    void addTeamUserLS(String teamId, String id);

    Integer getUserAuthority(Integer userId, String teamId);
}
