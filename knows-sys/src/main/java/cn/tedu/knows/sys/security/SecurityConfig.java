package cn.tedu.knows.sys.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // 配置全部放行
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // 禁用防跨域攻击
                .authorizeRequests()   //开始设置请求授权
                // 任何请求,全部放行
                .anyRequest().permitAll();
    }
}
