package com.gateway.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

/**
 * 自定义请求来源解析器
 */
@Component
public class HeaderOriginParser implements RequestOriginParser {

    /*
    * sentinel判断访问来源，只允许从网关访问，不直接访问其他微服务
    * */
    @Override
    public String parseOrigin(HttpServletRequest httpServletRequest) {
        String origin = httpServletRequest.getHeader("origin");
        if (origin.isEmpty())
            return "error";
        return origin;
    }
}
