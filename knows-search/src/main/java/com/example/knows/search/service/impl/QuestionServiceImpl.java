package com.example.knows.search.service.impl;

import cn.tedu.knows.commons.model.Tag;
import cn.tedu.knows.commons.model.User;
import com.example.knows.search.repository.QuestionRepository;
import com.example.knows.search.service.IQuestionService;
import com.example.knows.search.utils.Pages;
import com.example.knows.search.vo.QuestionVO;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;

@Service
@Slf4j
public class QuestionServiceImpl implements IQuestionService {
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private QuestionRepository questionRepository;

    @Override
    public void syncData() {
        // 通过Ribbon请求获得Question表分页查询的总页数
        String url = "http://faq-service/v2/questions/page/count?pageSize={1}";
        int pageSize = 8;
        Integer total = restTemplate.getForObject(url,Integer.class,pageSize);
        // 根据总页数进行循环
        for(int i =1; i<=total; i++){
            // 循环中查询当页的所有数据
            url = "http://faq-service/v2/questions/page?pageNum={1}&pageSize={2}";
            QuestionVO [] questions = restTemplate.getForObject(url,QuestionVO[].class,i,pageSize);
            // 新增到ES中
            questionRepository.saveAll(Arrays.asList(questions));
            log.debug("完成了第{}页的新增",i);
        }

    }

    @Override
    public PageInfo<QuestionVO> search(String key, String username, Integer pageNum, Integer pageSize) {
        // 先根据用户名获得用户对象
        String url="http://sys-service/v1/auth/user?username={1}";
        User user=restTemplate.getForObject(url,User.class,username);
        // 调用数据访问层方法执行搜索
        Pageable pageable= PageRequest.of(pageNum-1,pageSize,
                Sort.Direction.DESC,"createtime");
        Page<QuestionVO> page=questionRepository
                .queryAllByParams(key,key,user.getId(),pageable);
        // 根据每个问题的tagNames属性为它的Tags属性赋值
        for(QuestionVO vo:page){
            vo.setTags(tagNamesToTags(vo.getTagNames()));
        }
        // 将查询结果转换为PageInfo类型返回
        return Pages.pageInfo(page);
    }

    // 根据tagNames属性获得标签集合的方法
    private List<Tag> tagNamesToTags(String tagNames){
        // tagNames:"java基础,javaSE,面试题"
        String[] names=tagNames.split(",");
        //Ribbon请求获得所有标签
        String url="http://faq-service/v2/tags";
        Tag[] tagArr=restTemplate.getForObject(url,Tag[].class);
        // 创建一个Map对象,将所有标签赋值到其中
        Map<String,Tag> tagMap=new HashMap<>();
        for(Tag t:tagArr){
            tagMap.put(t.getName(),t);
        }
        // 实例化保存当前问题所有标签对象的集合
        List<Tag> tags=new ArrayList<>();
        // 遍历tagNames转换成的String数组,将对应的标签对象保存到tags集合中
        for(String name:names){
            tags.add(tagMap.get(name));
        }
        return tags;
    }


}
