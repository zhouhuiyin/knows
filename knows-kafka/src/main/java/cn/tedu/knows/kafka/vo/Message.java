package cn.tedu.knows.kafka.vo;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Message {
    private Integer id;
    private String content;
    private Long time;
}
