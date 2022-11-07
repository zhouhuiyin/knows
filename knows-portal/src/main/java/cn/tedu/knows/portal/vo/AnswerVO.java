package cn.tedu.knows.portal.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class AnswerVO {
    @NotNull(message = "问题id不能为空")
    private Integer questionId;

    @NotBlank(message = "问题内容不能为空")
    private String content;
}
