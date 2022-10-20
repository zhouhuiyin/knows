package cn.tedu.knows.portal.mapper;

import cn.tedu.knows.portal.vo.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface TagMapper extends BaseMapper<Tag> {
    // TagMapper接口用于连接数据库操作tag表
    // TagMapper接口继承BaseMapper<Tag>,因为父接口中包含了基本的增删改查操作
    // BaseMapper<Tag>中<Tag>是表示要操作的表对应的实体类,不能写错
}
