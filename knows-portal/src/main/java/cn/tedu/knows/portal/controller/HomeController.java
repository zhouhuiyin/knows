package cn.tedu.knows.portal.controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//本类的目标是根据当前登录用户角色,跳转不同页面
// @RestController标记针对异步请求进行处理的控制器类
// 由它标记的控制器方法的返回值或返回的对象,都会转换成json格式响应给axios
// 我们当前控制器是要根据不同身份跳转不同页面,重点在于要跳转页面,而不是响应json数据
// @Controller标记的控制器类的方法中支持返回特定字符串重定向到指定页面的效果
@Controller
public class HomeController {
    // 我们要判断登录用户是什么角色,所以要先定义两个角色的常量,用于判断
    public static final GrantedAuthority STUDENT=
            new SimpleGrantedAuthority("ROLE_STUDENT");
    public static final GrantedAuthority TEACHER=
            new SimpleGrantedAuthority("ROLE_TEACHER");

    // 当登录成功后一般都是访问首页
    // 我们设计用户访问localhost:8080/或localhost:8080/index.html都是访问这个方法
    @GetMapping(value = {"/","/index.html"})
    public String index(@AuthenticationPrincipal UserDetails user){
        // 判断UserDetails中是否包含讲师身份
        if(user.getAuthorities().contains(TEACHER)){
            // 如果是讲师,使用返回特定格式字符串实现页面重定向效果
            return "redirect:/index_teacher.html";
        } else if (user.getAuthorities().contains(STUDENT)) {
            return "redirect:/index_student.html";
        }
        // 既不是讲师也不是学生直接返回null(也可以返回登录页)
        return null;
    }
}
