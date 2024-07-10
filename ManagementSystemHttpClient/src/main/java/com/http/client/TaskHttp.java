package com.http.client;


import com.base.entity.Tasks;
import com.base.utils.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("http://127.0.0.1:10010/task")
public interface TaskHttp {

    /**
     * 根据Id查询任务
     * @param id
     * @return
     */
    @GetExchange("/sel/{id}")
    Result<Tasks> getById(@PathVariable String id);
}
