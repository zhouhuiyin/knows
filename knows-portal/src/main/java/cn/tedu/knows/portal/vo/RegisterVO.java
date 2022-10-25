package cn.tedu.knows.portal.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class RegisterVO implements Serializable {
    // NotBlank是判断字符串不能为空
    // message是验证不通过时,反馈给用户的信息
    @NotBlank(message="邀请码不能为空")
    private String inviteCode;          // 邀请码

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1\\d{10}$",message = "请输入正确手机号")
    private String phone;               // 手机号\用户名

    @NotBlank(message = "昵称不能为空")
    @Pattern(regexp = "^.{2,20}$",message = "昵称是2~20个字符")
    private String nickname;            // 昵称

    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^\\w{6,20}$",message = "密码必须是6~20个字符")
    private String password;            // 密码

    @NotBlank(message = "确认密码不能为空")
    private String confirm;             // 确认密码
}
