package cn.tedu.knows.faq.kafka;

import cn.tedu.knows.commons.model.Question;
import cn.tedu.knows.commons.vo.Topic;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class QuestionProducer {
    @Resource
    private KafkaTemplate<String,String> kafkaTemplate;
    //将问题信息发送到kafka中
    public void sendQuestion(Question question){
        // 将question对象转换为json字符串发送到kafka
        Gson gson = new Gson();
        String json = gson.toJson(question);
        log.debug("发送问题内容:{}",json);
        kafkaTemplate.send(Topic.QUESTION,json);
    }
}
