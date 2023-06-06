package com.example.knows.search;


import com.example.knows.search.repository.QuestionRepository;
import com.example.knows.search.service.IQuestionService;
import com.example.knows.search.vo.QuestionVO;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

//没有这个注解无法进行测试
@SpringBootTest
public class SyncTest {

    // 运行同步数据的方法
    @Resource
    IQuestionService questionService;

    @Resource
    QuestionRepository questionRepository;
    @Test
    public void addEs(){
        questionService.syncData();
    }

    @Test
    void search(){
        Page<QuestionVO> page =questionRepository.queryAllByParams("java","java", 11, PageRequest.of(0,8));
        for(QuestionVO vo:page){
            System.out.println(vo);
        }
    }

    // 测试搜索业务逻辑层
    @Test
    void testService(){
        PageInfo<QuestionVO> pageInfo=
                questionService.search("java","st2",
                        1,8);
        for (QuestionVO vo:pageInfo.getList()){
            System.out.println(vo);
        }
    }
}
