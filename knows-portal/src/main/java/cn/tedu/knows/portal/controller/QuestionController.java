package cn.tedu.knows.portal.controller;


import cn.tedu.knows.portal.model.Question;
import cn.tedu.knows.portal.service.IQuestionService;
import cn.tedu.knows.portal.vo.QuestionVO;
import com.github.pagehelper.PageInfo;
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
@RequestMapping("/v1/questions")
public class QuestionController {
    @Autowired
    private IQuestionService questionService;

    @GetMapping("/my")
    public PageInfo<Question> my(@AuthenticationPrincipal UserDetails user, Integer pageNum){
        // @AuthenticationPrincipal注解效果是从Spring-Security中
        // 获得登录用户的信息对象UserDetails,赋值给后面的参数
        Integer pageSize = 8;
        if(pageNum ==null){
            pageNum = 1;
        }
        return questionService.getMyQuestions(user.getUsername(),pageNum,pageSize);
    }

    // 学生发布问题访问的控制层方法
// @PostMapping("")表示访问它的路径为localhost:8080/v1/questions
    @PostMapping("")
    public String createQuestion(
            @Validated QuestionVO questionVO,
            BindingResult result){
        log.debug("接收表单信息为:{}",questionVO);
        if(result.hasErrors()){
            String msg=result.getFieldError().getDefaultMessage();
            return msg;
        }
        // 在这里调用业务逻辑层的新增问题方法
        // 返回运行信息
        return "ok";
    }

}
