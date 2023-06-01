package cn.tedu.knows.faq.service;

import cn.tedu.knows.commons.model.Comment;
import cn.tedu.knows.faq.vo.CommentVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author albert.zhu
 * @since 2022-10-21
 */
public interface ICommentService extends IService<Comment> {
    // 新增评论的业务逻辑层方法
    Comment saveComment(CommentVO commentVO, String username);

    // 按评论id删除评论的业务逻辑层方法
    boolean removeComment(Integer commentId,String username);

    // 修改评论内容的业务逻辑层方法
    Comment updateComment(Integer commentId,CommentVO commentVO,String username);

}
