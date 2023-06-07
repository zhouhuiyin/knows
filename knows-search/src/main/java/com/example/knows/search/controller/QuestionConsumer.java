package com.example.knows.search.controller;


import cn.tedu.knows.commons.vo.Topic;
import com.example.knows.search.service.IQuestionService;
import com.example.knows.search.vo.QuestionVO;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class QuestionConsumer {
    @Resource
    private IQuestionService questionService;
    // kafka监听器声明指定监听的话题
    @KafkaListener(topics = Topic.QUESTION)
    public void questionReceive(ConsumerRecord<String,String> record){
        // 从kafka中获得传递到当前项目的消息
        String json = record.value();
        Gson gson = new Gson();
        QuestionVO questionVO = gson.fromJson(json, QuestionVO.class);
        // 调用业务逻辑层执行新增
        questionService.saveQuestion(questionVO);
        log.debug("成功的新增了问题到ES:{}",questionVO);

    }
}
