package cn.tedu.knows.portal.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
// 启动Spring-Security配置的注解
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // 配置类要继承WebSecurityConfigurerAdapter
    // 这个父类提供了Spring-Security的标准配置方法
    // 我们在配置Spring-Security时直接重写它提供的方法即可
    // 下面的方法就是配置什么样的用户能够登录
    // 它能够代替application中配置的用户

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        String[] as = {"answer","remove"};
        // 在代码中配置一个用户登录
        auth.inMemoryAuthentication().withUser("tom").password("{bcrypt}$2a$10$ELGiEhKyLlO9r3.WVOkHDe16JTCKCErcABhElD5CF7ZwQ.Hm6sVRW").authorities(as);
        // 编写完上述配置
        // 我们就可以使用tom和888888登录了
        // tom用户拥有answer授权\资格
        // 这个授权可以访问需要这个授权才能执行的控制器方法
    }
}
