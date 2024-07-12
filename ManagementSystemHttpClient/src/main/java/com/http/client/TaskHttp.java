package com.http.client;


import com.base.annotation.NotNeedIntercept;
import com.base.entity.Tasks;
import com.base.utils.Result;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

@FeignClient("task")
@NotNeedIntercept
public interface TaskHttp {

    /**
     * 根据Id查询任务
     * @param id
     * @return
     */
    @RequestLine("GET /sel/{id}")
    @NotNeedIntercept
    @Headers({"ycdy: httpService","service-info: openFeign"})
    Result<Tasks> getById(@Param("id") String id);

    @RequestLine("DELETE /del/{taskIds}")
    @NotNeedIntercept
    @Headers({"ycdy: httpService","service-info: openFeign"})
    Result delById(@Param("taskIds") List<Integer> taskIds);

    @RequestLine("DELETE /del-project/{projectIds}")
    @NotNeedIntercept
    @Headers({"ycdy: httpService","service-info: openFeign"})
    Result deleteProjectIds(@Param("projectIds") List<Integer> list);
}
