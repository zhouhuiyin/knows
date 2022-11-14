package cn.tedu.knows.portal.controller;


import cn.tedu.knows.portal.exception.ServiceException;
import cn.tedu.knows.portal.mapper.CommentMapper;
import cn.tedu.knows.portal.model.Comment;
import cn.tedu.knows.portal.service.ICommentService;
import cn.tedu.knows.portal.vo.CommentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author albert.zhu
 * @since 2022-10-21
 */
@RestController
@RequestMapping("/v1/comments")
@Slf4j
public class CommentController {
    @Autowired
    private ICommentService commentService;
    // @PostMapping等价于@PostMapping("")
    @PostMapping
    public Comment postComment(@Validated CommentVO commentVO, BindingResult result, @AuthenticationPrincipal UserDetails user){
        log.debug("接收到表单信息{}",commentVO);
        if(result.hasErrors()){
            String msg = result.getFieldError().getDefaultMessage();
            throw new ServiceException(msg);
        }
        //这里调用业务逻辑层方法

        return commentService.saveComment(commentVO,user.getUsername());
    }



}
