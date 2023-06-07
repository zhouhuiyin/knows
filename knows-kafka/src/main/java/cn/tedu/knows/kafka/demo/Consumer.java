package cn.tedu.knows.kafka.demo;


import cn.tedu.knows.kafka.vo.Message;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Consumer {
    // 我们添加的Spring-kafka依赖中包含了一个kafka监听器
    // 当指定话题有消息出现时,这个监听器就会自动调用对应话题接收的方法
    // 还会将话题中的消息赋值给对应方法的参数
    @KafkaListener(topics = "myTopic")
    public void receive(ConsumerRecord<String,String> record){
        // 根据监听器的作用,主要话题"myTopic"中出现新的消息,这个方法就会被调用执行
        // 参数record就是发送到"myTopic"话题中的消息
        // ConsumerRecord<[话题名称的类型],[消息的类型]>
        // 获得record中的消息内容
        String json=record.value();
        // 将json字符串转回为java对象
        Gson gson=new Gson();
        Message message=gson.fromJson(json, Message.class);
        // 日志输出得到的java对象
        log.debug("接收到的消息:{}",message);
    }
}
