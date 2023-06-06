package com.example.knows.search.repository;

import com.example.knows.search.vo.QuestionVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

// 数据访问层的注解要添加!
@Repository
public interface QuestionRepository extends ElasticsearchRepository<QuestionVO,Integer> {
    // 用户搜索功能的数据访问层方法
    // 因为搜索查询逻辑比较复杂,所以不再使用方法名的方式表示,直接编写查询指令
    @Query("{\n" +
            "    \"bool\": {\n" +
            "      \"must\": [{\n" +
            "        \"bool\": {\n" +
            "          \"should\": [\n" +
            "          {\"match\": {\"title\": \"?0\"}},\n" +
            "          {\"match\": {\"content\": \"?1\"}}]\n" +
            "        }\n" +
            "      }, {\n" +
            "        \"bool\": {\n" +
            "          \"should\": [\n" +
            "          {\"term\": {\"publicStatus\": 1}},\n" +
            "          {\"term\": {\"userId\": ?2}}]\n" +
            "        }\n" +
            "      }]\n" +
            "    }\n" +
            "  }")
    Page<QuestionVO> queryAllByParams(String title, String content,
                                      Integer userId, Pageable pageable);
}
