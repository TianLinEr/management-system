package com.project;

import com.base.content.ContentBase;
import com.base.dto.ProjectDTO;
import com.base.mapper.ProjectsMapper;
import com.base.vo.PageVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MapperTest {
    @Autowired
    private ProjectsMapper projectsMapper;

    @Test
    void test01(){
        List<ProjectDTO> list = projectsMapper.getAllPublic(
                new PageVO(0, 5),
                String.valueOf(ContentBase.ProjectIsPublic),
                String.valueOf(ContentBase.ProjectNotIsDel));
        System.out.println(list);
    }

}
