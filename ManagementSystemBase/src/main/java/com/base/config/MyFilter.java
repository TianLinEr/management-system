package com.base.config;

import com.base.content.ContentBase;
import com.base.excepttion.FilterHeaderException;
import jakarta.servlet.*;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

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

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String header1 = httpRequest.getHeader("origin");
        String header2 = httpRequest.getHeader("Truth");
        if("gateway".equals(header1) && "666 wotaiqiangla".equals(header2))
            filterChain.doFilter(servletRequest, servletResponse);
        else
            throw new FilterHeaderException(ContentBase.ErrorCode);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
