package cn.tedu.knows.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

// 这个注解必须要添加,才能编写配置,否则编写的配置不会生效
@Configuration
public class TokenConfig {
    // 向Spring容器中保存一个令牌的生成策略
    //  策略指:1.保存在内存中   2.保存在客户端
    //  生成JWT令牌

    // 定义解析JWT的口令
    private final String SIGNING_KEY="knows_jwt";

    @Bean
    public TokenStore tokenStore(){
        return new JwtTokenStore(accessTokenConverter());
    }
    // JWT转换器(将json格式信息转换为JWT的对象)
    @Bean
    public JwtAccessTokenConverter accessTokenConverter(){
        JwtAccessTokenConverter converter=
                new JwtAccessTokenConverter();
        converter.setSigningKey(SIGNING_KEY);
        return converter;
    }


}
