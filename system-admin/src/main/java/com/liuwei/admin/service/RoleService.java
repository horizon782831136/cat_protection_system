package com.liuwei.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Role;
import org.springframework.stereotype.Service;

/**
 * (Role)表服务接口
 *
 * @author makejava
 * @since 2024-01-02 14:36:10
 */
@Service
public interface RoleService extends IService<Role>{
    public Result getRoleByCondition(Integer current, Integer size, Long roleId, String roleName, Integer status);
    

}
