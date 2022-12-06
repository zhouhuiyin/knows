package cn.tedu.knows.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

// 这个注解必须要添加,才能编写配置,否则编写的配置不会生效
@Configuration
public class TokenConfig {
    // 向Spring容器中保存一个令牌的生成策略
    //  策略指:1.保存在内存中   2.保存在客户端
    //  先保存在内存中
    @Bean
    public TokenStore tokenStore(){
        return new InMemoryTokenStore();
    }
}
