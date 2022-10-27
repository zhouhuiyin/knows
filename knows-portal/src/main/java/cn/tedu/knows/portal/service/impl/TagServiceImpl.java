package cn.tedu.knows.portal.service.impl;

import cn.tedu.knows.portal.model.Tag;
import cn.tedu.knows.portal.mapper.TagMapper;
import cn.tedu.knows.portal.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

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

    // 声明缓存对象
    private List<Tag> tags=new CopyOnWriteArrayList<>();
    // tags属性用于充当保存所有标签的缓存对象
    // 因为TagServiceImpl默认是单例的,所以tags属性也只有一份
    // CopyOnWriteArrayList是一个线程安全的集合类型对象,从jdk1.8开始

    //声明Map类型的缓存
    //ConcurrentHashMap是一个线程安全的Map集合类型对象,从jdk1.8开始
    private Map<String,Tag> tagMap= new ConcurrentHashMap<>();
    @Autowired
    private TagMapper tagMapper;
    @Override
    public List<Tag> getTags() {
        // 先判断当前tags属性是不是空集合
        if(tags.isEmpty()){
            synchronized (tags){
                if(tags.isEmpty()){
                    //如果是空集合，连接数据库查询所有标签赋值给tags
                    List<Tag> list = tagMapper.selectList(null);
                    tags.addAll(list);
                    //将查询到的所有标签保存在tagMap中
                    for(Tag t:tags){
                        tagMap.put(t.getName(),t);
                    }
                    System.out.println("tags加载数据完成");
                }
            }
        }
        return tags;
    }

    @Override
    public Map<String, Tag> getTagMap() {
        //判断tagMap是不是empyt
        if(tagMap.isEmpty()){
            //如果tagMap没有值,一定是上面的getTags()方法没有运行,所以调用它
            getTags();
        }
        return tagMap;
    }
}
