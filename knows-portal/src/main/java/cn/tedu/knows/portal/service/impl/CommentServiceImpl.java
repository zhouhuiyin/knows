package cn.tedu.knows.portal.service.impl;

import cn.tedu.knows.portal.model.Comment;
import cn.tedu.knows.portal.mapper.CommentMapper;
import cn.tedu.knows.portal.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
