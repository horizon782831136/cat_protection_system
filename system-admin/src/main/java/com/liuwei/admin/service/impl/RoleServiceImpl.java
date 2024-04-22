package com.liuwei.admin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuwei.admin.dao.RoleDao;
import com.liuwei.admin.domain.vo.RoleVO;
import com.liuwei.admin.service.RoleService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.dto.PageDTO;
import com.liuwei.framework.domain.po.Role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * (Role)表服务实现类
 *
 * @author makejava
 * @since 2024-01-02 14:36:10
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleDao,Role> implements RoleService {
    private final RoleDao roleDao;

    @Override
    public Result getRoleByCondition(Integer current, Integer size, Long roleId, String roleName, Integer status) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(ObjectUtil.isNotEmpty(roleId), Role::getRoleId, roleId)
                .like(ObjectUtil.isNotEmpty(roleName), Role::getRoleName, StringUtils.trimAllWhitespace(roleName))
                .eq(ObjectUtil.isNotEmpty(status), Role::getStatus, status)
                .orderByDesc(Role::getUpdateTime);
        Page<Role> page = new Page<>(current, size);
        roleDao.selectPage(page, queryWrapper);
        PageDTO<RoleVO> of = PageDTO.of(page, RoleVO.class);
        return new Result(of);
    }
}
