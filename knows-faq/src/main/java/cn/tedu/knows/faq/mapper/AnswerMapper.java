package cn.tedu.knows.faq.mapper;
import cn.tedu.knows.commons.model.Answer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
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
    public interface AnswerMapper extends BaseMapper<Answer> {

        //Mybatis关联查询，实现查询指定问题的所有回答包含所有的评论，它对应resources中mapper里AnswerMapper.xml中的同名的xml配置
        List<Answer> findAnswersByQuestionId(Integer questionId);
        // 根据answerid修改采纳状态的方法
        /*
        JVM底层编译运行程序时,默认是不会保存局部变量名称的
        由于方法的参数也是局部变量,所以参数的名称在编译时就消失了,运行时不能保存
        导致Mybatis默认情况下多个参数时,是不能直接使用参数名称对应#{}中的内容的
        但是SpringBoot官方脚手架创建的java项目JVM的参数进行了修改,
        使得方法的局部变量名称也能保存,所以直接编写变量名就可以对应#{}里的名称
        但是使用阿里的脚手架创建的SpringBoot项目就没有进行JVM的参数的修改,
        就不能直接编写变量名称,会导致程序报错
        最终为了保证程序能够顺利运行,最好在参数前添加@Param注解来标记对应的名称
         */
        @Update("update answer set accept_status=#{acceptStatus} " +
                " where id=#{answerId}")
        int updateAcceptStatus(@Param("acceptStatus") Integer acceptStatus,
                               @Param("answerId") Integer answerId);
    // update [表名] set xxx=xxx  where xxx=xxx




}
