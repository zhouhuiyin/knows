package cn.tedu.knows.faq.mapper;


import cn.tedu.knows.commons.model.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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
public interface QuestionMapper extends BaseMapper<Question> {
    //根据用户id查询问题数
    @Select("select count(*) from question where user_id=#{id}")
    int countQuestionsByUserId(Integer userId);

    // 收藏数作业的方法编写在下面即可
    @Select("select count(*) from user_collect where user_id = #{id}")
    int countCollectsByUserID(Integer userId);

    @Select("SELECT q.* FROM question q\n" +
            "LEFT JOIN user_question uq ON q.id=uq.question_id\n" +
            "WHERE uq.user_id=#{id} OR q.user_id=#{id}\n" +
            "ORDER BY q.createtime desc")
    List<Question> findTeacherQuestions(Integer id);

    @Update("update question set status=#{status} " +
            " where id=#{questionId}")
    int updateStatus(@Param("status") Integer status,
                     @Param("questionId") Integer questionId );

}
