package cn.tedu.knows.sys.controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/home")
public class HomeController {
    // 我们要判断登录用户是什么角色,所以要先定义两个角色的常量,用于判断
    public static final GrantedAuthority STUDENT=
            new SimpleGrantedAuthority("ROLE_STUDENT");
    public static final GrantedAuthority TEACHER=
            new SimpleGrantedAuthority("ROLE_TEACHER");

    @GetMapping
    public String index(@AuthenticationPrincipal UserDetails user){
        // 判断UserDetails中是否包含讲师身份
        if(user.getAuthorities().contains(TEACHER)){
            // 如果是讲师,使用返回特定格式字符串实现页面重定向效果
            return "/index_teacher.html";
        } else if (user.getAuthorities().contains(STUDENT)) {
            return "/index_student.html";
        }
        // 既不是讲师也不是学生直接返回null(也可以返回登录页)
        return null;
    }
}
