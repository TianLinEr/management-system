package com.service.service;

import com.base.dto.UserDTO;
import com.base.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author psl
 * @since 2024-07-03
 */
public interface UsersService extends IService<Users> {

    /**
     * 获取用户权限
     * @param userId
     * @return
     */
    Integer getUserAuthority(String userId);

    /**
     * 获取所有用户
     * @return
     */
    List<Users> selectAllUser(String userId);

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    Users selectById(String userId);

    /**
     * 修改用户信息
     * @param user
     * @param userId
     */
    void update(Users user,String userId);

    /**
     * 添加用户
     * @param user
     * @param userId
     */
    void insert(Users user,String userId);

    /**
     * 删除用户
     * @param userId
     * @param delUserIds
     */
    void deleteById(String userId,List<Integer> delUserIds);

    /**
     * 登录验证
     * @param userEmail
     * @param password
     * @return
     */
    UserDTO selectByEmail(String userEmail, String password);

    /**
     * 用户注册
     * @param users
     */
    void insert(Users users);
}
