package com.base.interceptor;

import com.base.annotation.NotNeedIntercept;
import com.base.config.JwtProperties;
import com.base.config.JwtUtil;
import com.base.content.ContentBase;
import com.base.content.JwtClaimsConstant;
import com.base.context.BaseContext;
import com.base.excepttion.LoginException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;


/**
 * jwt令牌校验的拦截器
 */
@Component
@Slf4j
public class JwtTokenAdminInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 校验jwt
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }
        //判断当前方法是否需要拦截

        // 检查类和方法是否被@NotNeedIntercept注解标记
        if (handlerMethod.getBeanType().isAnnotationPresent(NotNeedIntercept.class) ||
                handlerMethod.getMethod().isAnnotationPresent(NotNeedIntercept.class)) {
            return true; // 不拦截
        }

        //1、从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getAdminTokenName());

        //2、校验令牌
        try {
            log.info("jwt校验:{}", token);
            Jws<Claims> claimsJws = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
            Integer empId = Integer.valueOf(claimsJws.getPayload().get(JwtClaimsConstant.USER_ID).toString());

            log.info("当前员工id:{}", empId);

            //存储当前登入用户的id
            BaseContext.setCurrentId(empId);
            //3、通过，放行
            return true;
        } catch (Exception ex) {
            throw new LoginException(ContentBase.ErrorCode);
        }
    }
}
