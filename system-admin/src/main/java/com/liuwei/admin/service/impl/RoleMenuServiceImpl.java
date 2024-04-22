package com.liuwei.admin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuwei.admin.dao.RoleMenuDao;
import com.liuwei.admin.domain.vo.RoleMenuVO;
import com.liuwei.admin.service.MenuService;
import com.liuwei.admin.service.RoleMenuService;
import com.liuwei.admin.service.RoleService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.dto.PageDTO;
import com.liuwei.framework.domain.po.Menu;
import com.liuwei.framework.domain.po.Role;
import com.liuwei.framework.domain.po.RoleMenu;
import com.liuwei.framework.enums.Status;
import com.liuwei.framework.utils.BeanUtils;
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
    private final RoleService roleService;
    private final MenuService menuService;
    @Override
    public Result getRoleMenuByCondition(Integer current, Integer size, Long id, Long roleId, Long menuId, Integer status) {
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(ObjectUtil.isNotEmpty(id), RoleMenu::getId, id)
                .like(ObjectUtil.isNotEmpty(roleId), RoleMenu::getRoleId, roleId)
                .like(ObjectUtil.isNotEmpty(menuId), RoleMenu::getMenuId, menuId)
                .eq(ObjectUtil.isNotEmpty(status), RoleMenu::getStatus, status)
                .orderByDesc(RoleMenu::getCreateTime);
        Page<RoleMenu> page = new Page<>(current, size);
        roleMenuDao.selectPage(page, queryWrapper);
        List<RoleMenuVO> collect = page.getRecords().stream().map(roleMenu -> {
            RoleMenuVO roleMenuVO = BeanUtils.copyBean(roleMenu, RoleMenuVO.class);
            //获取角色信息和权限信息，将其放入vo中
            LambdaQueryWrapper<Role> roleQueryWrapper = new LambdaQueryWrapper<>();
            roleQueryWrapper.eq(Role::getRoleId, roleMenu.getRoleId())
                    .select(Role::getRoleName);
            Role role = roleService.getOne(roleQueryWrapper);
            LambdaQueryWrapper<Menu> menuQueryWrapper = new LambdaQueryWrapper<>();
            menuQueryWrapper.eq(Menu::getMenuId, roleMenu.getMenuId())
                    .select(Menu::getMenuName);
            Menu menu = menuService.getOne(menuQueryWrapper);
            roleMenuVO.setRoleName(role.getRoleName()).setMenuName(menu.getMenuName());
            return roleMenuVO;
        }).collect(Collectors.toList());
        Page<RoleMenuVO> newPage = new Page<>(current, size);
        newPage.setRecords(collect).setPages(page.getPages()).setTotal(page.getTotal());
        PageDTO<RoleMenuVO> of = PageDTO.of(newPage, RoleMenuVO.class);
        return new Result(of);
    }

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
