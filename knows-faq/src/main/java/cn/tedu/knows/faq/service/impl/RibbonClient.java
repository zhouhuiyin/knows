package cn.tedu.knows.faq.service.impl;

import cn.tedu.knows.commons.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Component
public class RibbonClient {
    @Resource
    private RestTemplate restTemplate;
    // 根据用户名返回用户对象
    public User getUser(String username){
        String url="http://sys-service/v1/auth/user?username={1}";
        User user=restTemplate.getForObject(
                url  ,  User.class  ,  username);
        return  user;
    }
}
