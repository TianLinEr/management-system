package com.base.mapper;

import com.base.entity.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author psl
 * @since 2024-07-03
 */
public interface UsersMapper extends BaseMapper<Users> {

    @Select("select user_type from users where user_id=#{user_id}")
    Integer getUserType(@Param("user_id") String id);
}
