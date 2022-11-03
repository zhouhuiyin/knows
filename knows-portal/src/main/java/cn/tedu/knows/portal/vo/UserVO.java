package cn.tedu.knows.portal.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
// 使当前类支出链式set赋值的注解
@Accessors(chain=true)
public class UserVO implements Serializable {
    private Integer id;
    private String username;
    private String nickname;
    // 问题数
    private int questions;
    // 收藏数
    private int collections;
}
