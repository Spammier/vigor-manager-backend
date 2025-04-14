package com.example.hello.interceptor;

import com.example.hello.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 登录检查拦截器
 */
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 获取请求url
        String url = request.getRequestURL().toString();
        // 判断是否是登录请求
        if (url.contains("/login")) {
            return true;
        }

        // 获取请求头中的token
        String token = request.getHeader("token");
        // 判断token是否存在
        if (!StringUtils.hasLength(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        // 判断token是否有效
        if (jwtUtils.validateJwtToken(token)) {
            return true;
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }
} 