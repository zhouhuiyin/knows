package cn.tedu.knows.portal.service.impl;

import cn.tedu.knows.portal.mapper.UserMapper;
import cn.tedu.knows.portal.model.Question;
import cn.tedu.knows.portal.mapper.QuestionMapper;
import cn.tedu.knows.portal.model.User;
import cn.tedu.knows.portal.service.IQuestionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author albert.zhu
 * @since 2022-10-21
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements IQuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Override
    public List<Question> getMyQuestions(String username) {
        // 根据用户名查询用户对象
        User user = userMapper.findUserByUsername(username);
        // 实例化QueryWrapper对象设置查询条件
        QueryWrapper<Question> query = new QueryWrapper<>();
        query.eq("user_id",user.getId());
        query.eq("delete_status",0);
        query.orderByDesc("createtime");
        // 按设置好的条件进行查询操作
        List<Question> list=questionMapper.selectList(query);
        // 千万别忘了返回list
        return list;
    }
}
