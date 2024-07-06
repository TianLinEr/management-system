package com.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.base.content.ContentBase;
import com.base.entity.Users;
import com.base.excepttion.SqlSelectException;
import com.base.mapper.UsersMapper;
import com.service.service.UsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author psl
 * @since 2024-07-03
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

    @Autowired
    private UsersMapper usersMapper;

    /**
     * 获取用户权限
     * @param id
     * @return
     */
    @Override
    public Integer getUserAuthority(String id) {
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Users::getUserId,id);
        Users users = usersMapper.selectOne(wrapper);
        if(users == null){
            throw new SqlSelectException(ContentBase.ErrorCode);
        }else
            return Integer.valueOf(users.getUserType());

    }
}
