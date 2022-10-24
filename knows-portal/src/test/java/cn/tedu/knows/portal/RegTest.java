package cn.tedu.knows.portal;

import cn.tedu.knows.portal.mapper.ClassroomMapper;
import cn.tedu.knows.portal.model.Classroom;
import cn.tedu.knows.portal.service.IUserService;
import cn.tedu.knows.portal.vo.RegisterVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RegTest {
    // 测试QueryWrapper根据邀请码查询班级信息
    @Autowired
    ClassroomMapper classroomMapper;
    @Autowired
    IUserService userService;
    @Test
    public void query(){
        // 实例化QueryWrapper对象,指定泛型是查询返回值的类型,同时指定表
        QueryWrapper<Classroom> query=new QueryWrapper<>();
        // QueryWrapper对象实际上就是一个条件
        // 我们需要将查询条件设置在query对象中
        // query.eq([列名],[值])  eq是等于的意思
        query.eq("invite_code","JSD2002-525416");
        // 使用classroomMapper对象执行连库操作
        // 执行查询时设置查询条件query,就会按照query对象中设置的条件进行查询
        // 因为这个查询最多查询出一行,所以我们调用selectOne方法用实体类接收
        Classroom classroom=classroomMapper.selectOne(query);
        System.out.println(classroom);
    }

    @Test
    public void reg(){
        RegisterVO reg=new RegisterVO();
        reg.setPhone("13313313133");
        reg.setNickname("大虎子");
        reg.setInviteCode("JSD2002-525416");
        reg.setPassword("123456");
        userService.registerStudent(reg);
        System.out.println("ok");
    }
}
