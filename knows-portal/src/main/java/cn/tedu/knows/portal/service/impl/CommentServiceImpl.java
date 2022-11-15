package cn.tedu.knows.portal.service.impl;

import cn.tedu.knows.portal.exception.ServiceException;
import cn.tedu.knows.portal.mapper.UserMapper;
import cn.tedu.knows.portal.model.Comment;
import cn.tedu.knows.portal.mapper.CommentMapper;
import cn.tedu.knows.portal.model.User;
import cn.tedu.knows.portal.service.ICommentService;
import cn.tedu.knows.portal.vo.CommentVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author albert.zhu
 * @since 2022-10-21
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Comment saveComment(CommentVO commentVO, String username) {
        User user = userMapper.findUserByUsername(username);
        Comment comment = new Comment()
                .setUserId(user.getId())
                .setUserNickName(user.getNickname())
                .setAnswerId(commentVO.getAnswerId())
                .setContent(commentVO.getContent())
                .setCreatetime(LocalDateTime.now());
        int num = commentMapper.insert(comment);
        if(num!=1){
            throw new ServiceException("数据库异常!");
        }
        //返回comment对象
        return comment;
    }

    @Override
    public boolean removeComment(Integer commentId, String username) {
        User user = userMapper.findUserByUsername(username);
        //判断是不是讲师
        if(user.getType().equals(1)){
            //如果是讲师，允许删除任何评论
            int num = commentMapper.deleteById(commentId);
            return num==1;
        }
        // 不是讲师删除评论要判断当前登录的用户是不是评论的发布者
        // 而评论的发布者id在comment对象中,我们需要先通过id查询到它
        Comment comment = commentMapper.selectById(commentId);
        if(comment.getUserId().equals(user.getId())){
            // 如果是评论的发布者在删除评论,直接删除
            int num = commentMapper.deleteById(commentId);
            return num==1;
        }
        throw new ServiceException("您不能删除别人的评论");
    }
}
