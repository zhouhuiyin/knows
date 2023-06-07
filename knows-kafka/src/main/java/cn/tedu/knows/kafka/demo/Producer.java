package cn.tedu.knows.kafka.demo;


import cn.tedu.knows.kafka.vo.Message;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class Producer {
    // 从Spring容器中获得能够操作kafka的对象
    // 这个对象是Spring-kafka框架提供的,我们无需编写
    // KafkaTemplate<[话题名称的类型],[传递消息的类型]>
    @Resource
    private KafkaTemplate<String,String> kafkaTemplate;

    // 编写一个每隔10秒运行一次的方法
    // 每次运行会向kafka发送一条消息
    int i=1;
    @Scheduled(fixedRate = 10000)
    public void sendMessage(){
        // 实例化Message对象用户消息的发送
        Message message=new Message()
                .setId(i++)
                .setContent("这是发送给Kafka的消息")
                .setTime(System.currentTimeMillis());
        // 实例化转换json格式字符串的工具类
        Gson gson=new Gson();
        String json=gson.toJson(message);
        log.debug("即将发送消息:{}",json);
        // 执行消息的发送
        kafkaTemplate.send("myTopic",json);
        log.debug("消息已发送!");
    }
}
