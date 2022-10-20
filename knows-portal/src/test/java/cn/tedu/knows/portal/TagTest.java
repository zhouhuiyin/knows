package cn.tedu.knows.portal;

import cn.tedu.knows.portal.mapper.TagMapper;

import cn.tedu.knows.portal.model.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

// SpringBoot环境下要想测试必须在测试类上添加这个注解
@SpringBootTest
public class TagTest {
    @Autowired
    TagMapper tagMapper;

    @Test
    public void getAll(){
        List<Tag> tags = tagMapper.selectList(null);
        for(Tag t:tags){
            System.out.println(t);
        }
    }

}
