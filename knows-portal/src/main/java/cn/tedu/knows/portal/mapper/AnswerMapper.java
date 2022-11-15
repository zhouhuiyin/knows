package cn.tedu.knows.portal.mapper;

import cn.tedu.knows.portal.model.Answer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* <p>
    *  Mapper 接口
    * </p>
*
* @author albert.zhu
* @since 2022-10-21
*/
    @Repository
    public interface AnswerMapper extends BaseMapper<Answer> {

        //Mybatis关联查询，实现查询指定问题的所有回答包含所有的评论，它对应resources中mapper里AnswerMapper.xml中的同名的xml配置
        List<Answer> findAnswersByQuestionId(Integer questionId);



    }
