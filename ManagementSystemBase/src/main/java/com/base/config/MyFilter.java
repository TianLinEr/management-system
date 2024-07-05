package com.base.config;

import com.base.content.ContentBase;
import com.base.excepttion.FilterHeaderException;
import com.base.utils.HttpRequestWrapper;
import jakarta.servlet.*;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        String header1 = httpRequest.getHeader("origin");
        String header2 = httpRequest.getHeader("Truth");

        String body = readBody(httpRequest);
        HttpRequestWrapper wrappedRequest = new HttpRequestWrapper(httpRequest, body);

        if("gateway".equals(header1) && "666 wotaiqiangla".equals(header2))
            filterChain.doFilter(wrappedRequest, servletResponse);
        else
            throw new FilterHeaderException(ContentBase.NotFound);
    }

    private String readBody(HttpServletRequest request) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            }
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }

        return stringBuilder.toString();
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
