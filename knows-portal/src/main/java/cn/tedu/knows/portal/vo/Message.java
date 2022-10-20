package cn.tedu.knows.portal.vo;

import lombok.Data;

@Data
public class Message {
    private Integer id;      // 信息id
    private String name;     // 信息发送者
    private String content;  // 信息内容

}
