package com.user.controller;

import com.base.annotation.NotNeedIntercept;
import com.base.config.JwtProperties;
import com.base.config.JwtUtil;
import com.base.content.ContentBase;
import com.base.content.JwtClaimsConstant;
import com.base.context.BaseContext;
import com.base.dto.UserDTO;
import com.base.entity.Users;
import com.base.excepttion.TimeOutException;
import com.base.utils.Result;
import com.base.vo.LoginVO;
import com.service.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.*;

@RestController
@Slf4j
@Tag(name = "登录管理")
@NotNeedIntercept
public class LoginController {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private UsersService userService;

    @PostMapping("/login_in")
    @Operation(summary = "用户登入")
    @NotNeedIntercept
    public Result<UserDTO> login_in(@RequestBody Users users) {

        UserDTO user = userService.selectByEmail(users.getUserEmail(), users.getPassword());
        if (user == null) {
            return new Result<UserDTO>().error(ContentBase.ErrorCode, "用户名或密码错误");
        }

//        System.out.println(jwtProperties);
        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getUserId());
        claims.put(JwtClaimsConstant.USER_AUTHORITY, user.getUserType());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);
        user.setToken(token);
        ArrayList<UserDTO> list = new ArrayList<>();
        list.add(user);
        log.info("用户登入-成功");
        return new Result<UserDTO>().success(ContentBase.SuccessCode, "用户登入", list);
    }

    @PostMapping("/login_up")
    @Operation(summary = "用户注册")
    @Transactional
    @NotNeedIntercept
    public Result<UserDTO> login_up(@RequestBody LoginVO loginVO) {
        Map<LocalDateTime, String> time = BaseContext.getTime();
        Map<LocalDateTime, String> map=new HashMap<>();

        LocalDateTime now=LocalDateTime.now();
        time.keySet().forEach(item->{
            if(item.isAfter(now))
                map.put(item,time.get(item));
        });
        BaseContext.setTime(map);
        if(!map.containsValue(loginVO.getCode()))
            throw new TimeOutException(ContentBase.ErrorCode);

        Users users = new Users();
        users.setUserEmail(loginVO.getEmail());
        users.setPassword(loginVO.getPassword());
        userService.insert(users);
        log.info("用户注册-成功");
        return new Result<UserDTO>().success(ContentBase.SuccessCode, "用户注册成功", null);
    }
}
