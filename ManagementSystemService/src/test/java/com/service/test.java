package com.service;

import com.base.annotation.NotNeedIntercept;
import com.base.entity.Teams;
import com.base.entity.Users;
import com.base.vo.TeamVO;
import com.service.service.TeamsService;
import com.service.service.UsersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class test {


    @Autowired
    private UsersService userService;

    @Autowired
    private TeamsService teamsService;


    @Test
    @NotNeedIntercept
    void test() {
        System.out.println("test");
//        Integer userAuthority = userService.getUserAuthority("1");
//        List<Users> users = userService.selectAllUser("5");

//        System.out.println(teamsService.getAll("5"));

//        for (TeamVO teams : teamsService.getAll("5")) {
//            System.out.println(teams);
//        }
    }
}
