package com.base.config;

import com.base.interceptor.JwtTokenAdminInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    //todo 与knife4j存在冲突，拦截器添加的路径不能正确放行

    @Autowired
    private JwtTokenAdminInterceptor jwtTokenAdminInterceptor;

    /**
     * 注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册自定义拦截器对象
        registry.addInterceptor(jwtTokenAdminInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/v3/**",
                        "/doc.html/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html/**",
                        "/error/**",
                        "/actuator/**",
                        "/user/login_in",
                        "/user/login_up",
                        "/login_in",
                        "/login_up");

    }
}
