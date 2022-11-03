package cn.tedu.knows.portal.controller;


import cn.tedu.knows.portal.model.User;
import cn.tedu.knows.portal.service.IUserService;
import cn.tedu.knows.portal.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
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
}
