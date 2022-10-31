package cn.tedu.knows.portal.controller;

import cn.tedu.knows.portal.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 统一异常处理类,类名是没有固定要求的
// @RestControllerAdvice表示当控制器运行出现特定情况时,可以运行这个类中的方法
// 所谓特定情况可以有多种定义,我们这里需要了解的就是异常的情况
@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {
    // 这个注解表示下面的方法是用来处理控制器发生异常时的情况
    // Handler:处理者(处理器)
    @ExceptionHandler
    // 方法的参数类型是ServiceException,表示控制器方法中发生ServiceException时
    // 当前方法就会捕捉异常对象并执行
    // 方法名称没有固定要求
    public String handlerServiceException(ServiceException e){
        log.error("业务异常",e);
        return e.getMessage();
    }
    // 为了保证控制层方法能够处理任何类型的异常
    // 下面编写一个处理异常的方法,参数是异常的父类
    @ExceptionHandler
    public String handlerException(Exception e){
        log.error("其他异常",e);
        return e.getMessage();
    }
}
