package cn.tedu.knows.faq.service.impl;

import cn.tedu.knows.commons.exception.ServiceException;
import cn.tedu.knows.commons.model.Answer;
import cn.tedu.knows.commons.model.Question;
import cn.tedu.knows.commons.model.User;
import cn.tedu.knows.faq.service.IAnswerService;
import cn.tedu.knows.faq.vo.AnswerVO;
import cn.tedu.knows.faq.mapper.AnswerMapper;
import cn.tedu.knows.faq.mapper.QuestionMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
    private QuestionMapper questionMapper;

    @Resource
    private RibbonClient ribbonClient;
    @Override
    @Transactional
    public Answer saveAnswer(AnswerVO answerVO, String username) {
        User user = ribbonClient.getUser(username);
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
        // 执行关联查询,获得包含所有评论的回答列表
        List<Answer> answers = answerMapper.findAnswersByQuestionId(questionId);
        return answers;
    }

    @Override
    @Transactional
    public boolean accept(Integer answerId, String username) {
        User user=ribbonClient.getUser(username);
        // 先根据answerId查询出answer的信息
        Answer answer=answerMapper.selectById(answerId);
        // 再根据answer中的questId查询question对象的信息
        Question question=questionMapper.selectById(answer.getQuestId());
        // 判断当前登录用户是不是问题的发布者
        if(user.getId().equals(question.getUserId())){
            // 如果当前问题是当前登录用户发布的,进行采纳流程
            // 先修改answer状态
            int num=answerMapper.updateAcceptStatus(
                    1,answerId);
            if(num!=1){
                throw new ServiceException("数据库忙!");
            }
            // 再修改question状态
            num=questionMapper.updateStatus(
                    Question.SOLVED,question.getId());
            if(num!=1){
                throw new ServiceException("数据库忙!");
            }
            return true;
        }
        return false;
    }
}
