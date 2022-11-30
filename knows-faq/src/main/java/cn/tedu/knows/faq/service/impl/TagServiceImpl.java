package cn.tedu.knows.faq.service.impl;


import cn.tedu.knows.commons.model.Tag;
import cn.tedu.knows.faq.mapper.TagMapper;
import cn.tedu.knows.faq.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {
    @Resource
    private RedisTemplate<String,List<Tag>> redisTemplate;

    @Autowired
    private TagMapper tagMapper;
    @Override
    public List<Tag> getTags() {
        //先从Redis中尝试获得标签集合
        List<Tag> tags = redisTemplate.opsForValue().get("tags");
        // 判断获得的集合是不是null
        if(tags==null){
            // 如果是null表示Redis中没有所有标签集合的缓存,需要连接数据保存到Redis中
            tags = tagMapper.selectList(null);
            redisTemplate.opsForValue().set("tags",tags);
            System.out.println("redis加载标签完毕！！！！！");
        }
        return tags;
    }

    @Override
    public Map<String, Tag> getTagMap() {
        // 实例化一个Map对象
        Map<String,Tag> tagMap=new HashMap<>();
        // 调用上面获得List<Tag>的方法,遍历它为Map对象赋值
        for(Tag t:getTags()){
            tagMap.put(t.getName(),t);
        }
        return tagMap;
    }
}
