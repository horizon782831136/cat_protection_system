package com.liuwei.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.RoleMenu;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (RoleMenu)表服务接口
 *
 * @author makejava
 * @since 2024-01-02 14:36:21
 */
@Service
public interface RoleMenuService extends IService<RoleMenu>{
    public Result getRoleMenuByCondition(Integer current, Integer size, Long id, Long roleId, Long menuId,
                                         Integer status
    );

    public List<String> getMenuByRoleId(Long roleId);
    

}
