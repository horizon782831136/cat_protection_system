package com.liuwei.user.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuwei.framework.domain.po.Menu;
import com.liuwei.framework.domain.po.RoleMenu;
import com.liuwei.framework.enums.Status;
import com.liuwei.user.dao.RoleMenuDao;
import com.liuwei.user.service.MenuService;
import com.liuwei.user.service.RoleMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * (RoleMenu)表服务实现类
 *
 * @author makejava
 * @since 2024-01-02 14:36:21
 */
@Service
@RequiredArgsConstructor
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuDao,RoleMenu> implements RoleMenuService {
    private final RoleMenuDao roleMenuDao;
    private final MenuService menuService;


    @Override
    public List<String> getMenuByRoleId(Long roleId) {
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId, roleId)
                .eq(RoleMenu::getStatus, Status.NORMAL_STATUS.getKey())
                .select(RoleMenu::getMenuId);
        List<RoleMenu> roleMenus = roleMenuDao.selectList(queryWrapper);
        if (ObjectUtil.isNotEmpty(roleMenus)) {
            List<Long> menuIds = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
            LambdaQueryWrapper<Menu> menuQueryWrapper = new LambdaQueryWrapper<>();
            menuQueryWrapper.in(Menu::getMenuId, menuIds)
                    .eq(Menu::getStatus, Status.NORMAL_STATUS.getKey())
                    .select(Menu::getAuthority);
            List<String> list = menuService.list(menuQueryWrapper).stream().map(menu -> menu.getAuthority())
                    .collect(Collectors.toList());
            return list;

        };
        return null;
    }
}
