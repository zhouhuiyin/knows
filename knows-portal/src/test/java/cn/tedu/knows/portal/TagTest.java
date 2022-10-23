package cn.tedu.knows.portal;

import cn.tedu.knows.portal.mapper.TagMapper;

import cn.tedu.knows.portal.mapper.UserMapper;
import cn.tedu.knows.portal.model.Permission;
import cn.tedu.knows.portal.model.Tag;
import cn.tedu.knows.portal.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    // 密码加密操作
    PasswordEncoder encoder=new BCryptPasswordEncoder();
    // 加密操作
    @Test
    public void pwd(){
        String str="123456";
        // 执行加密操作encode方法参数是要加密的字符串,返回值是加密结果
        String pwd=encoder.encode(str);
        System.out.println(pwd);
        //$2a$10$7sArRpk0xhLtR7.c.T9Uw.XYkqIpQFhPBGsYJmY/Qk1EgkbDUtl4m
        //$2a$10$QY6cYVIe7kItV81rQ2R3uOhmmLzN8GTjkowj17St1oggC2HCAlc8O
    }

    // 验证操作
    @Test
    public void match(){
        // 验证需要提供两个参数:1.原字符串  2.加密字符串
        // 方法能验证原字符串是否可以加密为加密字符串
        // 返回结果为boolean类型
        boolean b=encoder.matches("123456",
                "$2a$10$QY6cYVIe7kItV81rQ2R3uOhmmLzN8GTjkowj17St1oggC2HCAlc8O");
        System.out.println("验证结果为:"+b);
    }

    @Autowired
    UserMapper userMapper;
    @Test
    public void userTest(){
        //根据用户名获得用户对象信息
        //id:11 用户名 st2  id:3用户名 tc2
        User user = userMapper.findUserByUsername("st2");
        //根据用户id查询用户权限
        List<Permission> list = userMapper.findUserPermissionsById(user.getId());
        for(Permission p : list){
            System.out.println(p);
        }

    }

}
