package com.task.controller;

import com.base.content.ContentBase;
import com.base.entity.Tasks;
import com.base.utils.Result;
import com.service.service.TasksService;
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
@Tag(name = "任务管理")
public class TaskController {

    @Autowired
    private TasksService taskService;

    @GetMapping("/{id}")
    @Operation(summary = "根据id查询任务")
    Result<Tasks> getById(@PathVariable String id){
        Tasks one = taskService.getById(id);
        List<Tasks> all = new ArrayList<>();
        all.add(one);
        log.info("任务管理-根据id查询任务-查询成功");
        return new Result<Tasks>(ContentBase.SuccessCode,"查询成功",all);
    }
}
