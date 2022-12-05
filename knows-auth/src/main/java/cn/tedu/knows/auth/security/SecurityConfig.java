package cn.tedu.knows.auth.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // 设置全部放行,因为我们将登录和验证都交给了Oauth和Token
    // SpringSecurity不再负责验证登录的任务,所以直接放行
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // 防跨域攻击
                .authorizeRequests()   // 访问权限设置
                .anyRequest().permitAll()  // 全部放行
                .and().formLogin();        //支持表单登录
    }
}
