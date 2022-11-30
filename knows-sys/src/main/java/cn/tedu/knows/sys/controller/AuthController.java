package cn.tedu.knows.sys.controller;

import cn.tedu.knows.commons.model.User;
import cn.tedu.knows.sys.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    @Resource
    private IUserService userService;
    // /v1/auth/demo
    @GetMapping("/demo")
    public String demo(){
        return "controller demo";
    }

    @GetMapping("/user")
    public User getUser(String username){
        return userService.getUserByUsername(username);
    }
}
