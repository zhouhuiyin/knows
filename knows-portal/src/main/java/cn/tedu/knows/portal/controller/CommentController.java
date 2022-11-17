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
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}/delete")
    public String removeComment(@PathVariable Integer id,@AuthenticationPrincipal UserDetails user ){
        boolean isDelete = commentService.removeComment(id,user.getUsername());
        if(isDelete){
            return "ok";
        }else{
            return "fail";
        }
    }

    @PostMapping("/{id}/update")
    public Comment updateComment(@PathVariable Integer id,@Validated Comment commentVO,BindingResult result,UserDetails user){
        log.info("接收到表单信息:{}",commentVO);
        log.debug("要修改的评论id:{}",id);
        if(result.hasErrors()){
            String msg=result.getFieldError().getDefaultMessage();
            throw new ServiceException(msg);
        }
        // 这里调用业务逻辑层
        return null;

    }



}
