package cn.tedu.knows.sys.controller;


import cn.tedu.knows.commons.model.User;
import cn.tedu.knows.sys.service.IUserService;
import cn.tedu.knows.sys.vo.RegisterVO;
import cn.tedu.knows.sys.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author albert.zhu
 * @since 2022-10-21
 */
@RestController
@Slf4j
@RequestMapping("/v1/users")
public class UserController {
    @Autowired
    private IUserService userService;
    @GetMapping("/master")
    public List<User> master(){
        // 调用业务逻辑层方法获得所有讲师并返回
        List<User> users=userService.getTeachers();
        return users;
    }

    @GetMapping("/me")
    public UserVO me(@AuthenticationPrincipal UserDetails user){
        return userService.getUserVO(user.getUsername());
    }

    //处理注册的方法
    @PostMapping("/register")
    //RegisterVO参数前添加@Validated注解,表示启动SpringValidation验证
    // 在控制器运行之前框架就会按照设置好的规则进行验证工作
    public String register(@Validated RegisterVO registerVO, BindingResult result){
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
