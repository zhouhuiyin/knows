package cn.tedu.knows.portal.service;

import cn.tedu.knows.portal.model.Question;
import cn.tedu.knows.portal.vo.QuestionVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

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
    // 分页查询参数pageNum:页码 pageSize每页条数
    // 返回值PageInfo,既包含问题列表List,又包含分页信息的对象
    PageInfo<Question> getMyQuestions(String username, Integer pageNum, Integer pageSize);

    //学生发布问题的方法
    void saveQuestion(QuestionVO questionVO,String username);
}
