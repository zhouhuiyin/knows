package cn.tedu.knows.portal;

import cn.tedu.knows.portal.mapper.AnswerMapper;
import cn.tedu.knows.portal.mapper.QuestionMapper;
import cn.tedu.knows.portal.model.Answer;
import cn.tedu.knows.portal.model.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TeaTest {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private AnswerMapper answerMapper;
    @Test
    public void getQuestions(){
        List<Question> questions = questionMapper.findTeacherQuestions(3);
        for(Question q:questions){
            System.out.println(q);
        }
    }

    @Test
    public void testAnswer(){
        List<Answer> answers = answerMapper.findAnswersByQuestionId(149);
        for(Answer a:answers){
            System.out.println(a);
        }
    }
}
