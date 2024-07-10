package com.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.base.content.ContentBase;
import com.base.dto.UserDTO;
import com.base.entity.Users;
import com.base.excepttion.AddUserException;
import com.base.excepttion.NoAuthorityUpdateUserException;
import com.base.excepttion.SqlSelectException;
import com.base.excepttion.UserTypeException;
import com.base.mapper.UsersMapper;
import com.http.client.TaskHttp;
import com.http.client.UserHttp;
import com.service.service.UsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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

    @Autowired
    private TaskHttp taskHttp;

    /**
     * 获取用户权限
     * @param userId
     * @return
     */
    @Override
    public Integer getUserAuthority(String userId) {
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Users::getUserId,userId);
        Users users = usersMapper.selectOne(wrapper);
        if(users == null){
            throw new SqlSelectException(ContentBase.ErrorCode);
        }else
            return Integer.valueOf(users.getUserType());

    }

    @Override
    public List<Users> selectAllUser(String userId) {
        Integer userType = usersMapper.getUserType(userId);
        if(!Objects.equals(userType, ContentBase.AuthorityToAdmin))
            throw new UserTypeException(ContentBase.ErrorCode);

        return usersMapper.getAll();
    }

    @Override
    public Users selectById(String userId) {
        Users users = usersMapper.selectById(userId);
        if(users == null)
            throw new SqlSelectException(ContentBase.ErrorCode);
        return users;
    }

    @Override
    public void update(Users user,String userId) {
        Integer userType = usersMapper.getUserType(userId);
        if(!Objects.equals(userType, ContentBase.AuthorityToAdmin))
            if(user.getUserId() != null && !Objects.equals(user.getUserId(),userId))
                throw new NoAuthorityUpdateUserException(ContentBase.ErrorCode);
        usersMapper.update(user);
    }

    @Override
    public void insert(Users user,String userId) {
        Integer userType = usersMapper.getUserType(userId);
        if(!Objects.equals(userType, ContentBase.AuthorityToAdmin)){
            if(user.getUserType() == String.valueOf(ContentBase.AuthorityToAdmin))
                throw new AddUserException(ContentBase.ErrorCode);
            else
                usersMapper.insert(user);
        }else
            usersMapper.insert(user);
    }

    @Override
    public void deleteById(String userId,String delUserId) {
        Integer userType = usersMapper.getUserType(userId);
        if(!Objects.equals(userType, ContentBase.AuthorityToAdmin))
            if(!Objects.equals(delUserId,userId))
                throw new NoAuthorityUpdateUserException(ContentBase.ErrorCode);
        usersMapper.deleteById(delUserId);
    }

    @Override
    public UserDTO selectByEmail(String userEmail, String password) {
        Users users = usersMapper.selectOne(new LambdaQueryWrapper<Users>()
                .eq(Users::getUserEmail, userEmail)
                .eq(Users::getPassword, password));
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(users,userDTO);
        return userDTO;
    }

    @Override
    @Transactional
    public void insert(Users users) {
        List<Users> list = usersMapper.selectList(new LambdaQueryWrapper<Users>()
                .eq(Users::getUserEmail, users.getUserEmail()));
        if(list.size() != 0)
            throw new AddUserException(ContentBase.ErrorCode);

        if(users.getUserName() == null)
            users.setUserName(String.valueOf(Instant.now().getEpochSecond()));
        users.setUserType(String.valueOf(ContentBase.AuthorityToUser));
        users.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
//        System.out.println(users.toString());
        usersMapper.insert(users);
    }
}
