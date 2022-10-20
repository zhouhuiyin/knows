package cn.tedu.knows.portal.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author albert.zhu
 * @since 2022-10-21
 */
@RestController
@RequestMapping("/v1/users")
public class UserController {

    // @RequestMapping("/v1/users")编写在类上
    // 表示当前控制器类中任何方法的路径都要以/v1/users开头
    // @GetMapping表示当前方法是处理get请求的控制器方法,
    // ("/demo")也是规定访问它的路径,想要访问下面的方法最终的要求是
    // 以get方法请求路径localhost:8080/v1/users/demo
    @GetMapping("/demo")
    public String demo(){
        return "hello demo!";
    }

}
