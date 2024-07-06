package com.document.controller;

import com.base.content.ContentBase;
import com.base.entity.Documents;
import com.base.utils.Result;
import com.service.service.DocumentsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@Tag(name = "文档管理")
public class DocumentController {

    @Autowired
    private DocumentsService documentsService;

    @Operation(summary = "根据Id获取文档")
    @GetMapping("/{id}")
    Result<Documents> getById(@PathVariable String id){
        Documents documents = documentsService.getById(id);
        List<Documents> documentsList = new ArrayList<>();
        documentsList.add(documents);
        log.info("文档管理-根据Id获取文档-查询成功");
        return new Result<Documents>().success(ContentBase.SuccessCode, "查询成功", documentsList);
    }
}
