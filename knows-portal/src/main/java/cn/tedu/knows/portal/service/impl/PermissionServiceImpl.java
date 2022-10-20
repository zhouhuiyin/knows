package cn.tedu.knows.portal.service.impl;

import cn.tedu.knows.portal.model.Permission;
import cn.tedu.knows.portal.mapper.PermissionMapper;
import cn.tedu.knows.portal.service.IPermissionService;
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
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}
