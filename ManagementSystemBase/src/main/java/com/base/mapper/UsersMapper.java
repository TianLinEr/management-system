package com.base.mapper;

import com.base.entity.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
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
public interface UsersMapper extends BaseMapper<Users> {

    /**
     * 查询用户类型
     * @param id
     * @return
     */
    @Select("select user_type from users where user_id=#{user_id}")
    Integer getUserType(@Param("user_id") String id);

    /**
     * 查询所有用户
     * @return
     */
    @Select("select * from users")
    List<Users> getAll();

    /**
     * 修改用户信息
     * @param user
     */
    void update(Users user);
}
