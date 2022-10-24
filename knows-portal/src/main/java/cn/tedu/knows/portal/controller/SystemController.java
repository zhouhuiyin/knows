package cn.tedu.knows.portal.controller;

import cn.tedu.knows.portal.exception.ServiceException;
import cn.tedu.knows.portal.service.IUserService;
import cn.tedu.knows.portal.vo.RegisterVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// lombok提供的注解@Slf4j,它能够在当前类的属性中添加一个记录日志的属性log
// 我们在控制器方法中可以使用它来输出信息到日志中
@Slf4j
public class SystemController {

    @Autowired
    private IUserService userService;
    //处理注册的方法
    @PostMapping("/register")
    public String register(RegisterVO registerVO){
        // 使用@Slf4j提供的log对象记录日志
        log.debug("接收到表单信息:{}",registerVO);
        try{
            userService.registerStudent(registerVO);
            return "ok";
        }catch (ServiceException e){
            // 将错误信息输出到日志的error级别
            log.error("注册失败",e);
            // 将错误信息响应给前端
            return e.getMessage();
        }
    }

}
