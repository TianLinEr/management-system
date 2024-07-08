package com.base.config;

import com.base.content.ContentBase;
import com.base.excepttion.FilterHeaderException;
import jakarta.servlet.*;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import java.io.IOException;

/**
 * 过滤器
 */
@WebFilter
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {

        String contentType = servletRequest.getContentType();
        String method = "multipart/form-data";
        MultipartHttpServletRequest multipart = null;

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        String header1 = httpRequest.getHeader("origin");
        String header2 = httpRequest.getHeader("Truth");

        //如果是文件类型上传，则需要用这个request
        if (contentType != null && contentType.contains(method)) {
            // 将转化后的 request 放入过滤链中
            multipart = new StandardServletMultipartResolver().resolveMultipart(httpRequest);
        }

        if (multipart == null && "gateway".equals(header1) && "666 wotaiqiangla".equals(header2))
            filterChain.doFilter(httpRequest, servletResponse);
        else if (multipart != null && "gateway".equals(header1) && "666 wotaiqiangla".equals(header2))
            filterChain.doFilter(multipart, servletResponse);
        else
            throw new FilterHeaderException(ContentBase.NotFound);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
