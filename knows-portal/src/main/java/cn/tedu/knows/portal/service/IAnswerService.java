package cn.tedu.knows.portal.service;

import cn.tedu.knows.portal.model.Answer;
import cn.tedu.knows.portal.vo.AnswerVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author albert.zhu
 * @since 2022-10-21
 */
public interface IAnswerService extends IService<Answer> {
    //新增回答的业务逻辑层方法
    // 因为后面需要将我们新增的回答对象显示在回答列表中
    // 所以这个方法返回值为当前新增成功的Answer对象
    Answer saveAnswer(AnswerVO answerVO,String username);

    // 根据问题id查询所有回答的方法
    List<Answer> getAnswersByQuestionId(Integer questionId);

    // 根据答案id采纳答案的业务逻辑层方法
    boolean accept(Integer answerId,String username);

}
