package com.example.hello.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // @Autowired // 不再需要注入
    // private LoginCheckInterceptor loginCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注释掉拦截器的注册，完全依赖 Spring Security 和 JwtAuthenticationFilter
        // registry.addInterceptor(loginCheckInterceptor)
        //         .addPathPatterns("/**") 
        //         .excludePathPatterns(
        //             "/api/login",    
        //             "/api/register", 
        //             "/upload",       
        //             "/upload.html"   
        //         );
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // 对 /api 下的所有路径启用 CORS
                // .allowedOrigins("http://localhost:xxxx") // 精确指定允许的前端源 (推荐用于生产)
                .allowedOriginPatterns("*") // 允许所有来源 (开发时常用，比 allowedOrigins("*") 更安全，特别是需要凭证时)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的 HTTP 方法
                .allowedHeaders("*") // 允许所有请求头
                .allowCredentials(true) // 是否允许发送 Cookie 和携带 Authorization 头 (对 JWT 很重要)
                .maxAge(3600); // 预检请求的缓存时间 (秒)
    }
} 