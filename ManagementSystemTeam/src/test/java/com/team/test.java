package com.team;

import com.base.vo.TeamVO;
import com.service.service.TeamsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class test {
    @Autowired
    private TeamsService teamsService;

    @Test
    public void test(){
        List<TeamVO> all = teamsService.getAllByUserId("3");
        System.out.println(all);
    }
}
