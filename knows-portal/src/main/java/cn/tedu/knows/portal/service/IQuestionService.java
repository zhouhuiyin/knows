package cn.tedu.knows.portal.service;

import cn.tedu.knows.portal.model.Question;
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
public interface IQuestionService extends IService<Question> {
    // 根据登录用户的用户名查询问题列表
    List<Question> getMyQuestions(String username);

}
