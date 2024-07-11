package com.http.client;


import com.base.annotation.NotNeedIntercept;
import com.base.entity.Tasks;
import com.base.utils.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange("http://127.0.0.1:10010/task")
@NotNeedIntercept
public interface TaskHttp {

    /**
     * 根据Id查询任务
     * @param id
     * @return
     */
    @GetExchange("/sel/{id}")
    Result<Tasks> getById(@PathVariable String id);

    @DeleteExchange("/del/{taskIds}")
    Result delById(@PathVariable List<Integer> taskIds);

    @DeleteExchange("/del-project/{projectIds}")
    Result deleteProjectIds(List<Integer> list);
}
