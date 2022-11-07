package cn.tedu.knows.portal.controller;


import cn.tedu.knows.portal.service.IAnswerService;
import cn.tedu.knows.portal.vo.AnswerVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/v1/answer")
@Slf4j
public class AnswerController {
    @Autowired
    private IAnswerService answerService;

    // 新增讲师回复的控制器方法
    // 路径就是/v1/answers
    @PostMapping("")
    // 只有讲师(持有回答权限)的用户才能回复问题
    // 一种思路是判断是否有回答权限
    @PreAuthorize("hasAuthority('/question/answer')")
    // 另一种思路是判断是否有讲师角色
    // @PreAuthorize("hasRole('ROLE_TEACHER')")
    // hasRole是专门判断登录用户角色的指令
    // hasRole后面的角色名称可以省略ROLE_开头的部分
    public String postAnswer(@Validated AnswerVO answerVO, BindingResult result, @AuthenticationPrincipal UserDetails user){
        log.debug("表单信息:{}",answerVO);
        if(result.hasErrors()){
            String msg = result.getFieldError().getDefaultMessage();
            return msg;
        }
        // 这里调用业务逻辑层方法
        return  "ok";
    }

}
