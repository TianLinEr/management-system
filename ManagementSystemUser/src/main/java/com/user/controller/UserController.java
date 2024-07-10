package com.user.controller;

import com.base.content.ContentBase;
import com.base.context.BaseContext;
import com.base.entity.Users;
import com.base.utils.Result;
import com.service.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@Tag(name = "用户管理")
public class UserController {

    @Autowired
    private UsersService userService;

    @GetMapping("/authority/{id}")
    @Operation(summary = "获取用户权限")
    public Integer getUserAuthority(@PathVariable String id){
        log.info("用户管理-获取用户权限-获取成功");
        return userService.getUserAuthority(id);
    }

    @GetMapping("/all")
    @Operation(summary = "获取所有用户")
    public Result<Users> getAllUser(){
        String userId = BaseContext.getCurrentId().toString();

        List<Users> users = userService.selectAllUser(userId);
        log.info("用户管理-获取所有用户-获取成功");
        return new Result<Users>().success(ContentBase.SuccessCode,"获取成功",users);
    }

    @GetMapping("/sel/{userId}")
    @Operation(summary = "获取用户信息")
    public Result<Users> getUserInfo(@PathVariable String userId){
        List<Users> users=new ArrayList<>();
        Users user=userService.selectById(userId);
        users.add(user);
        log.info("用户管理-获取用户信息-获取成功");
        return new Result<Users>().success(ContentBase.SuccessCode,"获取成功",users);
    }

    @DeleteMapping("/del/{delUserId}")
    @Operation(summary = "删除用户")
    public Result delUser(@PathVariable String delUserId){
        String userId = BaseContext.getCurrentId().toString();

        userService.deleteById(userId,delUserId);

        log.info("用户管理-删除用户-删除成功");
        return new Result<>().success(ContentBase.SuccessCode,"删除成功",null);
    }

    @PostMapping("/add")
    @Operation(summary = "添加用户")
    public Result addUser(@RequestBody Users user){
        String userId = BaseContext.getCurrentId().toString();

        userService.insert(user,userId);

        log.info("用户管理-添加用户-添加成功");
        return new Result<>().success(ContentBase.SuccessCode,"添加成功",null);
    }

    @PutMapping("/update")
    @Operation(summary = "更新用户")
    public Result updateUser(@RequestBody Users user){
        String userId = BaseContext.getCurrentId().toString();

        userService.update(user,userId);

        log.info("用户管理-更新用户-更新成功");
        return new Result<>().success(ContentBase.SuccessCode,"更新成功",null);
    }
}
