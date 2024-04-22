package com.liuwei.admin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuwei.admin.dao.MenuDao;
import com.liuwei.admin.domain.vo.MenuVO;
import com.liuwei.admin.service.MenuService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.dto.PageDTO;
import com.liuwei.framework.domain.po.Menu;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * (Menu)表服务实现类
 *
 * @author makejava
 * @since 2024-01-02 14:35:18
 */
@Service
@RequiredArgsConstructor
public class MenuServiceImpl extends ServiceImpl<MenuDao,Menu> implements MenuService {
    private final MenuDao menuDao;

    @Override
    public Result getMenuByCondition(Integer current, Integer size, Long menuId, String menuName, Integer status) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(ObjectUtil.isNotEmpty(menuId), Menu::getMenuId, menuId)
                .like(ObjectUtil.isNotEmpty(menuName), Menu::getMenuName, StringUtils.trimAllWhitespace(menuName))
                .eq(ObjectUtil.isNotEmpty(status), Menu::getStatus, status)
                .orderByDesc(Menu::getCreateTime);
        Page<Menu> page = new Page<>(current, size);
        menuDao.selectPage(page, queryWrapper);
        PageDTO<MenuVO> of = PageDTO.of(page, MenuVO.class);
        return new Result(of);
    }
}
