package cn.tedu.knows.portal.vo;

import cn.tedu.knows.portal.mapper.TagMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MessageTest {
    @Autowired
    TagMapper tagMapper;

    @Test
    public void contextLoads(){
        Message m = new Message();
        m.setId(1);
        System.out.println(m);
    }

    @Test
    public void getId(){
        //根据id查询标签对象
        Tag tag=tagMapper.selectById(1);
        System.out.println(tag);
    }

    @Test
    public void addTag(){
        Tag tag=new Tag();
        tag.setId(21);
        tag.setName("微服务");
        tag.setCreateby("admin");
        tag.setCreatetime("2021-12-27 11:51:50");
        // 所有增删改操作都会返回一个int类型的返回值
        // 返回的int值表示本次数据库操作对数据库影响的行数
        int num=tagMapper.insert(tag);
        if(num==1){
            System.out.println("新增成功");
        }else{
            System.out.println("新增失败");
        }
    }

    @Test
    public void getAll(){
        // 全查tag表中所有对象
        // selectList是查询返回List的方法
        // 方法参数可以设置特定条件,参数设置为null表示无条件,既全查
        List<Tag> tags=tagMapper.selectList(null);
        for(Tag t: tags){
            System.out.println(t);
        }
    }
}
