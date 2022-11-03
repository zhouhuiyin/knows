package cn.tedu.knows.portal.mapper;

import cn.tedu.knows.portal.model.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

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

}
