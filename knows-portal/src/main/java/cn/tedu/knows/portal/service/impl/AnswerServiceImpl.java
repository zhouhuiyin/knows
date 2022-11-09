package cn.tedu.knows.portal.service.impl;

import cn.tedu.knows.portal.exception.ServiceException;
import cn.tedu.knows.portal.mapper.UserMapper;
import cn.tedu.knows.portal.model.Answer;
import cn.tedu.knows.portal.mapper.AnswerMapper;
import cn.tedu.knows.portal.model.User;
import cn.tedu.knows.portal.service.IAnswerService;
import cn.tedu.knows.portal.vo.AnswerVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements IAnswerService {
    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    private UserMapper userMapper;
    @Override
    @Transactional
    public Answer saveAnswer(AnswerVO answerVO, String username) {
        User user = userMapper.findUserByUsername(username);
        Answer answer = new Answer()
                .setContent(answerVO.getContent())
                .setLikeCount(0)
                .setUserId(user.getId())
                .setUserNickName(user.getNickname())
                .setQuestId(answerVO.getQuestionId())
                .setCreatetime(LocalDateTime.now())
                .setAcceptStatus(0);
        int num = answerMapper.insert(answer);
        if(num!=1){
            throw  new ServiceException("数据库异常！");
        }
        return answer;
    }

    @Override
    public List<Answer> getAnswersByQuestionId(Integer questionId) {
        // 实例化QueryWrapper对象,设置查询条件,并执行查询
        QueryWrapper<Answer> query = new QueryWrapper<>();
        query.eq("quest_id",questionId);
        List<Answer> answers = answerMapper.selectList(query);
        return answers;
    }
}
