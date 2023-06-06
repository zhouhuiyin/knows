package com.example.knows.search.service;

import com.example.knows.search.vo.QuestionVO;
import com.github.pagehelper.PageInfo;

public interface IQuestionService {
    // 声明同步数据库中Question数据到ES的方法
    void syncData();

    // 按用户输入的关键字进行搜索功能的业务逻辑层方法
    PageInfo<QuestionVO> search(String key, String username, Integer pageNum, Integer pageSize);
}
