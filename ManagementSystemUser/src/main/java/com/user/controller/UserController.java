package com.user.controller;

import com.base.content.ContentBase;
import com.base.context.BaseContext;
import com.base.dto.UserDTO;
import com.base.entity.Users;
import com.base.utils.Result;
import com.base.vo.UserVO;
import com.service.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@Slf4j
@Tag(name = "用户管理")
public class UserController {

    @Autowired
    private UsersService userService;

    @GetMapping("/authority/{id}")
    @Operation(summary = "获取用户权限")
    public Integer getUserAuthority(@PathVariable String id) {
        log.info("用户管理-获取用户权限-获取成功");
        return userService.getUserAuthority(id);
    }

    @GetMapping("/all")
    @Operation(summary = "获取所有用户")
    public Result<UserVO> getAllUser() {
        String userId = BaseContext.getCurrentId().toString();

        List<Users> users = userService.selectAllUser(userId);
        List<UserVO> userVOS = new ArrayList<>();
        users.forEach(user -> {
            UserVO userVO = new UserVO(
                    user.getUserId(), user.getUserName(), user.getUserSex(),
                    null, user.getUserPhone(), null,
                    user.getCreateDate().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    , user.getUserEmail(), user.getUserType(),null
            );
            if (user.getUserBirch() != null)
                userVO.setUserBirch(user.getUserBirch().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

            userVOS.add(userVO);
        });
        log.info("用户管理-获取所有用户-获取成功");
        return new Result<UserVO>().success(ContentBase.SuccessCode, "获取成功", userVOS);
    }

    @GetMapping("/sel/{userIds}")
    @Operation(summary = "获取用户信息")
    public Result<UserVO> getUserInfo(@PathVariable List<Integer> userIds) {
        List<UserVO> users = new ArrayList<>();
        List<Users> list = userService.selectById(userIds);
        list.forEach(user -> {
            UserVO userVO = new UserVO(
                    user.getUserId(), user.getUserName(), user.getUserSex(),
                    null, user.getUserPhone(), null,
                    user.getCreateDate().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    , user.getUserEmail(), user.getUserType(),null
            );
            if (user.getUserBirch() != null)
                userVO.setUserBirch(user.getUserBirch().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

            users.add(userVO);
        });
        log.info("用户管理-获取用户信息-获取成功");
        return new Result<UserVO>().success(ContentBase.SuccessCode, "获取成功", users);
    }

    @GetMapping("/sel-current")
    @Operation(summary = "获取用户信息")
    public Result<UserVO> getUser() {
        Integer currentId = BaseContext.getCurrentId();
        List<UserVO> users = new ArrayList<>();
        List<Users> list = userService.selectById(Collections.singletonList(currentId));
        list.forEach(user -> {
            UserVO userVO = new UserVO(
                    user.getUserId(), user.getUserName(), user.getUserSex(),
                    null, user.getUserPhone(), null,
                    user.getCreateDate().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    , user.getUserEmail(), user.getUserType(),null
            );
            if (user.getUserBirch() != null)
                userVO.setUserBirch(user.getUserBirch().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

            users.add(userVO);
        });
        log.info("用户管理-获取用户信息-获取成功");
        return new Result<UserVO>().success(ContentBase.SuccessCode, "获取成功", users);
    }

    @DeleteMapping("/del/{delUserIds}")
    @Operation(summary = "删除用户")
    public Result delUser(@PathVariable List<Integer> delUserIds) {
        String userId = BaseContext.getCurrentId().toString();

        userService.deleteById(userId, delUserIds);

        log.info("用户管理-删除用户-删除成功");
        return new Result<>().success(ContentBase.SuccessCode, "删除成功", null);
    }

    @PostMapping("/add")
    @Operation(summary = "添加用户")
    public Result addUser(@RequestBody Users user) {
        String userId = BaseContext.getCurrentId().toString();

        userService.insert(user, userId);

        log.info("用户管理-添加用户-添加成功");
        return new Result<>().success(ContentBase.SuccessCode, "添加成功", null);
    }

    @PutMapping("/update")
    @Operation(summary = "更新用户")
    public Result updateUser(@RequestBody Users user) {
        String userId = BaseContext.getCurrentId().toString();

        userService.update(user, userId);

        log.info("用户管理-更新用户-更新成功");
        return new Result<>().success(ContentBase.SuccessCode, "更新成功", null);
    }
}
