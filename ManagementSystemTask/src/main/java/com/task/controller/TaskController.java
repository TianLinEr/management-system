package com.task.controller;

import com.base.content.ContentBase;
import com.base.context.BaseContext;
import com.base.entity.Tasks;
import com.base.utils.Result;
import com.base.dto.TaskDTO;
import com.base.vo.TaskVORes;
import com.service.service.TasksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@Tag(name = "任务管理")
public class TaskController {

    @Autowired
    private TasksService taskService;

    @GetMapping("/sel/{id}")
    @Operation(summary = "根据id查询任务")
    public Result<Tasks> getById(@PathVariable String id){
        Tasks one = taskService.getById(id);
        List<Tasks> all = new ArrayList<>();
        all.add(one);
        log.info("任务管理-根据id查询任务-查询成功");
        return new Result<Tasks>().success(ContentBase.SuccessCode,"查询成功",all);
    }

    @GetMapping("/sel")
    @Operation(summary = "查询所有任务")
    public Result<TaskVORes> getAll(){
        Integer userId = BaseContext.getCurrentId();
        List<TaskVORes> all = taskService.getAll(String.valueOf(userId));
        log.info("任务管理-查询所有任务-查询成功");
        return new Result<TaskVORes>().success(ContentBase.SuccessCode,"查询成功",all);
    }

    @GetMapping("/sel/team/{teamId}")
    @Operation(summary = "查询关于团队的任务")
    public Result<TaskVORes> getAllTeam(@PathVariable String teamId){
        List<TaskVORes> all = taskService.getAllTeam(teamId);
        log.info("任务管理-查询所有任务-查询成功");
        return new Result<TaskVORes>().success(ContentBase.SuccessCode,"查询成功",all);
    }

    @GetMapping("/sel/project/{projectId}")
    @Operation(summary = "查询关于项目的任务")
    public Result<TaskVORes> getAllProject(@PathVariable String projectId){
        List<TaskVORes> all = taskService.getAllProject(projectId);
        log.info("任务管理-查询所有任务-查询成功");
        return new Result<TaskVORes>().success(ContentBase.SuccessCode,"查询成功",all);
    }

    @DeleteMapping("/del/{taskIds}")
    @Operation(summary = "根据id删除任务")
    public Result<Tasks> delById(@PathVariable List<Integer> taskIds){
        Integer userId = BaseContext.getCurrentId();
        taskService.delById(String.valueOf(userId),taskIds);
        log.info("任务管理-根据id删除任务-删除成功");
        return new Result<Tasks>().success(ContentBase.SuccessCode,"删除成功",null);
    }

    @DeleteMapping("/del-project/{projectIds}")
    @Operation(summary = "根据项目id删除任务")
    public Result deleteProjectIds(List<Integer> list){
        taskService.delProjectIds(list);
        log.info("任务管理-根据项目id删除任务-删除成功");
        return new Result<Tasks>().success(ContentBase.SuccessCode,"删除成功",null);
    }

    @PostMapping("/add")
    @Operation(summary = "新增任务")
    public Result<Tasks> addTask(@RequestBody TaskDTO tasks){
        taskService.insert(tasks);
        log.info("任务管理-新增任务-新增成功");
        return new Result<Tasks>().success(ContentBase.SuccessCode,"新增成功",null);
    }

    @PutMapping("/update")
    @Operation(summary = "修改任务")
    public Result<Tasks> update(@RequestBody Tasks tasks){
        String userId=BaseContext.getCurrentId().toString();
        taskService.update(userId,tasks);
        log.info("任务管理-修改任务-修改成功");
        return new Result<Tasks>().success(ContentBase.SuccessCode,"修改成功",null);
    }

    @PutMapping("/update/{taskId}")
    @Operation(summary = "恢复任务")
    public Result<Tasks> revoke(@PathVariable String taskId){
        Integer userId = BaseContext.getCurrentId();
        taskService.revokeById(String.valueOf(userId),taskId);
        log.info("任务管理-恢复任务-修改成功");
        return new Result<Tasks>().success(ContentBase.SuccessCode,"修改成功",null);
    }
}
