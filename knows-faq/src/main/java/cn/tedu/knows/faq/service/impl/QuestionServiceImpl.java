package cn.tedu.knows.faq.service.impl;


import cn.tedu.knows.commons.exception.ServiceException;
import cn.tedu.knows.commons.model.*;
import cn.tedu.knows.faq.mapper.QuestionMapper;
import cn.tedu.knows.faq.mapper.QuestionTagMapper;
import cn.tedu.knows.faq.mapper.UserQuestionMapper;
import cn.tedu.knows.faq.service.IQuestionService;
import cn.tedu.knows.faq.service.ITagService;
import cn.tedu.knows.faq.vo.QuestionVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
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
@Slf4j
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements IQuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private ITagService tagService; //获得包含所有标签的tagMap

    @Autowired
    private QuestionTagMapper questionTagMapper;

    @Autowired
    private UserQuestionMapper userQuestionMapper;

    @Resource
    private RestTemplate restTemplate;


    @Override
    public PageInfo<Question> getMyQuestions(String username, Integer pageNum, Integer pageSize) {
        // 根据用户名查询用户对象
        User user = this.getUser(username);
        // 实例化QueryWrapper对象设置查询条件
        QueryWrapper<Question> query = new QueryWrapper<>();
        query.eq("user_id",user.getId());
        query.eq("delete_status",0);
        query.orderByDesc("createtime");
        // PageHelper.startPage就是设置分页查询的方法
        // 必须编写在执行分页查询之前
        // startPage([页码],[每页条数])
        // 第一页页码为1
        PageHelper.startPage(pageNum,pageSize);
        // 按设置好的条件进行查询操作
        // 按设置好的条件进行查询操作
        List<Question> list=questionMapper.selectList(query);
        for(Question question:list){
            List<Tag> tags = tagNamesToTags(question.getTagNames());
            question.setTags(tags);
        }
        // 千万别忘了返回pageInfo
        return new PageInfo<>(list);
    }



    @Override
    // 添加事务注解
    // 实现效果:当前方法中所有sql操作要么都执行,要么都不执行
    // 只要方法运行过程中发生异常,那么已经执行的sql语句都会"回滚"
    @Transactional
    public void saveQuestion(QuestionVO questionVO, String username) {
        //1.根据用户名获取用户信息
        User user = this.getUser(username);
        //2.根据用户选中的标签数组，拼接一个标签字符串
        // {"Java基础","Java SE","面试题"}
        // "Java基础,Java SE,面试题,"
        StringBuilder builder = new StringBuilder();
        for(String tagName:questionVO.getTagNames()){
            builder.append(tagName).append(",");
        }
        //经过字符串拼接，我们需要删除拼接最后的, 也就是当前字符串长度-1位置的字符
         String tagNames =builder.deleteCharAt(builder.length()-1).toString();
        // 3.实例化Question对象,收集需要的信息(共10列)
        Question question = new Question()
                .setTitle(questionVO.getTitle())
                .setContent(questionVO.getContent())
                .setUserNickName(user.getNickname())
                .setUserId(user.getId())
                .setCreatetime(LocalDateTime.now())
                .setStatus(0)
                .setPageViews(0)
                .setPublicStatus(0)
                .setDeleteStatus(0)
                .setTagNames(tagNames);
        // 4.新增Question
        int num = questionMapper.insert(question);
        if(num!=1){
            throw new ServiceException("数据库异常");
        }
        // 5.新增问题和标签的关系
        // 因为要获得标签的id,所以要先将包含所有标签的Map获取过来
        // 然后根据用户选中标签名称获得对应的标签对象以确定标签的id
        Map<String, Tag> tagMap=tagService.getTagMap();
        // 遍历用户选中的所有标签
        for(String tagName : questionVO.getTagNames()){
            // 根据tagName获得tag对象
            Tag t=tagMap.get(tagName);
            // 实例化QuestionTag对象并赋值然后执行新增操作
            QuestionTag questionTag=new QuestionTag()
                    .setQuestionId(question.getId())
                    .setTagId(t.getId());
            num=questionTagMapper.insert(questionTag);
            if(num!=1){
                throw new ServiceException("数据库异常");
            }
            log.debug("新增了问题和标签的关系:{}",questionTag);
        }
        // 6.新增问题和讲师的关系
        //通过Ribbon获得所有讲师的数组
        String url = "http://sys-service/v1/users/master";
        User[] teachers = restTemplate.getForObject(url,User[].class);
        Map<String,User> teacherMap=new HashMap<>();
        for(User u:teachers){
            teacherMap.put(u.getNickname(),u);
        }
        for(String nickname : questionVO.getTeacherNicknames()){
            User teacher=teacherMap.get(nickname);
            UserQuestion userQuestion=new UserQuestion()
                    .setQuestionId(question.getId())

                    .setUserId(teacher.getId())
                    .setCreatetime(LocalDateTime.now());
            num=userQuestionMapper.insert(userQuestion);
            if(num!=1){
                throw new ServiceException("数据库异常");
            }
            log.debug("新增了问题和讲师的关系:{}",userQuestion);
        }
    }

    @Override
    public PageInfo<Question> getTeacherQuestions(String username, Integer pageNum, Integer pageSize) {
        User user = this.getUser(username);
        //设置分页条件
        PageHelper.startPage(pageNum,pageSize);
        // 执行查询
        List<Question> list = questionMapper.findTeacherQuestions(user.getId());
        //list就是查询讲师任务列表的分页结果
        for (Question q:list){
            List<Tag> tags=tagNamesToTags(q.getTagNames());
            q.setTags(tags);
        }
        return new PageInfo<>(list);

    }

    @Override
    public Question getQuestionById(Integer id) {
        // 根据MybatisPlus提供的方法来获取Question对象
        Question question=questionMapper.selectById(id);
        // 获得当前Question对象的所有tags集合
        List<Tag> tags=tagNamesToTags(question.getTagNames());
        question.setTags(tags);
        // 千万别忘了返回question
        return question;
    }

    @Override
    public Integer countQuestionsByUserId(Integer userId) {
        return questionMapper.countQuestionsByUserId(userId);
    }

    @Override
    public PageInfo<Question> getQuestions(Integer pageNum, Integer pageSize) {
        // 分页查询设置
        PageHelper.startPage(pageNum,pageSize);
        List<Question> list=questionMapper.selectList(null);
        // 别忘了编写返回值
        return new PageInfo<>(list);
    }

    private User getUser(String username){
        //利用Ribbon根据用户名获得用户对象的方法
        String url = "http://sys-service/v1/auth/user?username={1}";
        User user = restTemplate.getForObject(url,User.class,username);
        return user;
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
