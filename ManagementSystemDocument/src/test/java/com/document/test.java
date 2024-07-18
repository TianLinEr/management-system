package com.document;

import com.base.vo.DocumentVO;
import com.service.service.DocumentsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class test {

    @Autowired
    private DocumentsService documentsService;

    @Test
    public void test() {
        List<DocumentVO> all = documentsService.getAll();
        System.out.println(all);
    }
}
