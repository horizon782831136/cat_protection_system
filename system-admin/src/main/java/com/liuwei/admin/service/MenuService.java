package com.liuwei.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Menu;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * (Menu)表服务接口
 *
 * @author makejava
 * @since 2024-01-02 14:35:18
 */
@Service
public interface MenuService extends IService<Menu>{
    public Result getMenuByCondition(Integer current, Integer size, Long menuId, String menuName, Integer status);
    

}
