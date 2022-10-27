package cn.tedu.knows.portal.controller;


import cn.tedu.knows.portal.model.Question;
import cn.tedu.knows.portal.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/v1/questions")
public class QuestionController {
    @Autowired
    private IQuestionService questionService;

    @GetMapping("/my")
    public List<Question> my(@AuthenticationPrincipal UserDetails user){
        // @AuthenticationPrincipal注解效果是从Spring-Security中
        // 获得登录用户的信息对象UserDetails,赋值给后面的参数
        return questionService.getMyQuestions(user.getUsername());
    }

}
