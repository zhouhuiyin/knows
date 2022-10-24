package cn.tedu.knows.portal.service;

import cn.tedu.knows.portal.model.User;
import cn.tedu.knows.portal.vo.RegisterVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author albert.zhu
 * @since 2022-10-21
 */
public interface IUserService extends IService<User> {
    // Ctrl+Alt+B 直接跳转到当前接口的实现类

    // 学生注册功能的业务逻辑层方法声明
    void registerStudent(RegisterVO registerVO);

}
