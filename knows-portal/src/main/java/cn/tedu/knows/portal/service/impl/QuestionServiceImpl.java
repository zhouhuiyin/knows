package cn.tedu.knows.portal.service.impl;

import cn.tedu.knows.portal.mapper.UserMapper;
import cn.tedu.knows.portal.model.Question;
import cn.tedu.knows.portal.mapper.QuestionMapper;
import cn.tedu.knows.portal.model.Tag;
import cn.tedu.knows.portal.model.User;
import cn.tedu.knows.portal.service.IQuestionService;
import cn.tedu.knows.portal.service.ITagService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private ITagService tagService; //获得包含所有标签的tagMap
    @Override
    public List<Question> getMyQuestions(String username) {
        // 根据用户名查询用户对象
        User user = userMapper.findUserByUsername(username);
        // 实例化QueryWrapper对象设置查询条件
        QueryWrapper<Question> query = new QueryWrapper<>();
        query.eq("user_id",user.getId());
        query.eq("delete_status",0);
        query.orderByDesc("createtime");
        // PageHelper.startPage就是设置分页查询的方法
        // 必须编写在执行分页查询之前
        // startPage([页码],[每页条数])
        // 第一页页码为1
        PageHelper.startPage(1,8);
        // 按设置好的条件进行查询操作
        // 按设置好的条件进行查询操作
        List<Question> list=questionMapper.selectList(query);
        for(Question question:list){
            List<Tag> tags = tagNamesToTags(question.getTagNames());
            question.setTags(tags);
        }
        // 千万别忘了返回list
        return list;
    }

    //将tagNames属性转换为List<Tag>的方法
    private List<Tag> tagNamesToTags(String tagNames){
        // tagNames:"Java基础,Java SE,面试题"
        String[] names = tagNames.split(",");
        // names={"Java基础","Java SE","面试题"}
        // 获得包含所有标签对象的tagMap
        Map<String,Tag> tagMap = tagService.getTagMap();
        // 实例化一个List对象,用于保存从Map中获取的Tag对象
        List<Tag> tags = new ArrayList<>();
        for(String name:names){
            Tag t = tagMap.get(name);
            tags.add(t);
        }
        return tags;
    }
}
