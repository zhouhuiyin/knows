package com.example.knows.search.controller;

import com.example.knows.search.service.IQuestionService;
import com.example.knows.search.vo.QuestionVO;
import com.github.pagehelper.PageInfo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/v3/questions")
public class QuestionController {
    @Resource
    private IQuestionService questionService;
    @PostMapping
    public PageInfo<QuestionVO> search(
            String key,
            Integer pageNum,
            @AuthenticationPrincipal UserDetails user){
        if (pageNum==null)
            pageNum=1;
        Integer pageSize=8;
        PageInfo<QuestionVO> pageInfo=questionService
                .search(key,user.getUsername(),pageNum,pageSize);
        // 别忘了返回pageInfo
        return pageInfo;
    }
}
