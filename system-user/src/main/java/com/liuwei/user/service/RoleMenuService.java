package com.liuwei.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
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
    public List<String> getMenuByRoleId(Long roleId);
    

}
