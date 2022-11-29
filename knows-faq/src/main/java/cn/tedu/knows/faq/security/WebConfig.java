package cn.tedu.knows.faq.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//SpringMVC的配置类
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 配置当前项目的所有路径跨域请求
        registry.addMapping("/**") // 表示所有路径
                .allowedOrigins("*")         // 允许任何访问源
                .allowedMethods("*")         // 允许所有请求方法(get\post)
                .allowedHeaders("*");        // 允许任何请求头信息
    }
}
