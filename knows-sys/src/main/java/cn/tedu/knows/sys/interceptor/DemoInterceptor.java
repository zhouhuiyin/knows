package cn.tedu.knows.sys.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 拦截器也要保存到Spring容器中进行统一管理
@Component
public class DemoInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //控制器运行之前运行
        System.out.println("preHandle运行");
        // 返回值是boolean类型
        // 返回true表示允许当前请求访问控制器,否则不允许访问,终止请求流程
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //控制器方法运行之后运行
        System.out.println("postHandle运行");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //在页面显示结果之前(同步请求使用较多，异步请求使用较少)
        System.out.println("afterCompletion运行");
    }
}
