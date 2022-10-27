package cn.tedu.knows.portal.service;

import cn.tedu.knows.portal.model.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author albert.zhu
 * @since 2022-10-21
 */
public interface ITagService extends IService<Tag> {
    // 获得所有标签的业务逻辑层方法声明
    List<Tag> getTags();

    //获得包含所有标签的Map的方法
    Map<String,Tag> getTagMap();
}
