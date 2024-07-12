package com.http.client;

import com.base.annotation.NotNeedIntercept;
import com.base.dto.ProjectDTO;
import com.base.utils.Result;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient("project")
@NotNeedIntercept
public interface ProjectHttp {

    /**
     * 获取所有项目
     * @return
     */
    @RequestLine("GET /all")
    @NotNeedIntercept
    @Headers({"ycdy: httpService","service-info: openFeign"})
    Result<ProjectDTO> getAll();

    /**
     * 获取所有公开项目
     * @return
     */
    @RequestLine("GET /all/public")
    @NotNeedIntercept
    @Headers({"ycdy: httpService","service-info: openFeign"})
    Result<ProjectDTO> getAllPublic();


    /**
     * 获取我的项目
     * @return
     */
    @RequestLine("GET /all/my_project")
    @NotNeedIntercept
    @Headers({"ycdy: httpService","service-info: openFeign"})
    Result<ProjectDTO> getMyProject();

    /**
     * 根据Id获取项目
     * @return
     */
    @RequestLine("GET /sel/{id}")
    @NotNeedIntercept
    @Headers({"ycdy: httpService","service-info: openFeign"})
    Result<ProjectDTO> getById(@Param("id") String id);

}
