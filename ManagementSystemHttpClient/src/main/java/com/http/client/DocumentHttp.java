package com.http.client;


import com.base.annotation.NotNeedIntercept;
import com.base.entity.Documents;
import com.base.utils.Result;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import java.util.List;

@FeignClient("document")
@NotNeedIntercept
public interface DocumentHttp {

    @RequestLine("GET /sel/{id}")
    @NotNeedIntercept
    @Headers({"ycdy: httpService","service-info: openFeign"})
    Result<Documents> getById(@Param("id") String id);

    @RequestLine("DELETE /del/{documentIds}")
    @NotNeedIntercept
    @Headers({"ycdy: httpService","service-info: openFeign"})
    Result<Documents> deleteById(@Param("documnetIds") List<Integer> documentIds);

    @RequestLine("GET /del-project/{projectIds}")
    @NotNeedIntercept
    @Headers({"ycdy: httpService","service-info: openFeign"})
    Result deleteProjectIds(@Param("projectIds") List<Integer> projectIds);
}
