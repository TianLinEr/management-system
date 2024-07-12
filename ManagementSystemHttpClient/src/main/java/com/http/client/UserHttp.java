package com.http.client;


import com.base.annotation.NotNeedIntercept;
import com.base.utils.Result;
import com.base.vo.UserVO;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;


@NotNeedIntercept
@FeignClient("user")
public interface UserHttp {


    /**
     * 获取用户权限，如管理员等
     * @param id
     * @return
     */
    @RequestLine("GET /authority/{id}")
    @NotNeedIntercept
    @Headers({"ycdy: httpService","service-info: openFeign"})
    Integer getUserAuthority(@Param("id") String id);

    /**
     * 获取用户信息
     * @param userIds
     * @return
     */
    @RequestLine(value = "GET /sel/{userIds}")
    @NotNeedIntercept
    @Headers({"ycdy: httpService","service-info: openFeign"})
    Result<UserVO> getUserInfo(@Param("userIds") List<Integer> userIds);
}
