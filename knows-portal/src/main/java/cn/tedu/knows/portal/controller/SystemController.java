package cn.tedu.knows.portal.controller;

import cn.tedu.knows.portal.exception.ServiceException;
import cn.tedu.knows.portal.service.IUserService;
import cn.tedu.knows.portal.vo.RegisterVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
    //RegisterVO参数前添加@Validated注解,表示启动SpringValidation验证
    // 在控制器运行之前框架就会按照设置好的规则进行验证工作
    public String register(@Validated RegisterVO registerVO,BindingResult result){
        // BindingResult result这个参数是接收上面验证结果的对象,我们在代码中可以判断这个result中是否包含错误信息,以得知验证结果
        // 使用@Slf4j提供的log对象记录日志
        log.debug("接收到表单信息:{}",registerVO);
        if(result.hasErrors()){
            // 进入这个if证明验证没通过,要返回错误信息
            String msg=result.getFieldError().getDefaultMessage();
            return msg;
        }
        userService.registerStudent(registerVO);
        return "ok";
    }

}
