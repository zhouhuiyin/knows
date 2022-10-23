package cn.tedu.knows.portal.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterVO implements Serializable {
    private String inviteCode;          // 邀请码
    private String phone;               // 手机号\用户名
    private String nickname;            // 昵称
    private String password;            // 密码
    private String confirm;             // 确认密码
}
