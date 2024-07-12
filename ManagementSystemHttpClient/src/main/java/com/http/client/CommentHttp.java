package com.http.client;


import com.base.annotation.NotNeedIntercept;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.service.annotation.HttpExchange;

@FeignClient("comment")
@NotNeedIntercept
public interface CommentHttp {


}
