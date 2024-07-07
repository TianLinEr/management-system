package com.user.controller;

import com.service.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Tag(name = "用户管理")
public class UserController {

    @Autowired
    private UsersService userService;

    @GetMapping("/authority/{id}")
    @Operation(summary = "获取用户权限")
    Integer getUserAuthority(@PathVariable String id){
        log.info("用户管理-获取用户权限-获取成功");
        return userService.getUserAuthority(id);
    }
}
