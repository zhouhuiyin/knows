package cn.tedu.knows.sys.security;

import cn.tedu.knows.sys.interceptor.AuthInterceptor;
import cn.tedu.knows.sys.interceptor.DemoInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

//SpringMVC的配置类
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private DemoInterceptor demoInterceptor;

    @Resource
    private AuthInterceptor authInterceptor;


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 配置当前项目的所有路径跨域请求
        registry.addMapping("/**") // 表示所有路径
                .allowedOrigins("*")         // 允许任何访问源
                .allowedMethods("*")         // 允许所有请求方法(get\post)
                .allowedHeaders("*");        // 允许任何请求头信息
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 配置拦截器的方法
        registry.addInterceptor(demoInterceptor).addPathPatterns(
                //生效路径
                "/v1/auth/demo"
        );
        registry.addInterceptor(authInterceptor).addPathPatterns("/v1/home");
    }
}
