package com.http.client;


import com.base.annotation.NotNeedIntercept;
import com.base.entity.Users;
import com.base.utils.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("http://127.0.0.1:10010/user")
@NotNeedIntercept
public interface UserHttp {


    /**
     * 获取用户权限，如管理员等
     * @param id
     * @return
     */
    @GetExchange("/authority/{id}")
    Integer getUserAuthority(@PathVariable String id);

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    @GetExchange("/sel/{userId}")
    public Result<Users> getUserInfo(@PathVariable String userId);
}
