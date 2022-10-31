package cn.tedu.knows.portal.service.impl;

import cn.tedu.knows.portal.exception.ServiceException;
import cn.tedu.knows.portal.mapper.ClassroomMapper;
import cn.tedu.knows.portal.mapper.UserRoleMapper;
import cn.tedu.knows.portal.model.Classroom;
import cn.tedu.knows.portal.model.User;
import cn.tedu.knows.portal.mapper.UserMapper;
import cn.tedu.knows.portal.model.UserRole;
import cn.tedu.knows.portal.service.IUserService;
import cn.tedu.knows.portal.vo.RegisterVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ClassroomMapper classroomMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Override
    @Transactional
    public void registerStudent(RegisterVO registerVO) {
        // 1.根据用户输入的邀请码查询班级信息
        QueryWrapper<Classroom> query = new QueryWrapper<>();
        query.eq("invite_code",registerVO.getInviteCode());
        Classroom classroom = classroomMapper.selectOne(query);
        // 2.判断班级信息是否为空,如果为空抛出异常
        if(classroom==null){
            throw new ServiceException("邀请码错误！");
        }
        // 3.根据用户输入的手机号查询用户信息
        User user = userMapper.findUserByUsername(registerVO.getPhone());
        // 4.判断用户手机号是否已经被注册,如果被注册抛出异常
        if(user!=null){
            throw new ServiceException("手机号已经被注册！");
        }
        // 5.用户输入的密码进行加密操作
        PasswordEncoder encoder=new BCryptPasswordEncoder();
        String pwd = "{bcrypt}"+encoder.encode(registerVO.getPassword());
        // 6.创建User对象,为它赋值
        User stu = new User()
                .setUsername(registerVO.getPhone())
                .setNickname(registerVO.getNickname())
                .setPassword(pwd)
                .setClassroomId(classroom.getId())
                .setCreatetime(LocalDateTime.now())
                .setEnabled(1)
                .setLocked(0)
                .setType(0);
        // 7.将User对象新增到数据库
        int num = userMapper.insert(stu);
        if(num!=1){
            throw new ServiceException("数据库异常");
        }
        // 8.最后新增user和role关系表的数据
        UserRole userRole = new UserRole()
                .setRoleId(2)
                .setUserId(stu.getId());
        num = userRoleMapper.insert(userRole);
        if(num!=1){
            throw new ServiceException("数据库异常");
        }


    }

    private List<User> teachers=new CopyOnWriteArrayList<>();
    private Map<String,User> teacherMap=new ConcurrentHashMap<>();

    @Override
    public List<User> getTeachers() {
        if(teachers.isEmpty()){
            synchronized (teachers){
                if(teachers.isEmpty()){
                    List<User> users=userMapper.findTeachers();
                    teachers.addAll(users);
                    for(User u:users){
                        teacherMap.put(u.getNickname(),u);
                    }
                }
            }
        }
        // 千万别忘了返回teachers
        return teachers;
    }

    @Override
    public Map<String, User> getTeacherMap() {
        if(teacherMap.isEmpty()){
            getTeachers();
        }
        // 千万别忘了返回teacherMap
        return teacherMap;
    }


}
