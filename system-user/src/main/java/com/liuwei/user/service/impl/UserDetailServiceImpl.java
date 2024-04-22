package com.liuwei.user.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liuwei.framework.domain.po.User;
import com.liuwei.user.dao.UserDao;
import com.liuwei.user.domain.dto.LoginUser;
import com.liuwei.user.service.RoleMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserDao userDao;
    private final RoleMenuService roleMenuService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User user = userDao.selectOne(queryWrapper);
        if(ObjectUtil.isEmpty(user)){
            throw new UsernameNotFoundException("用户名不存在!");
        }
        //根据用户获取权限信息
        List<String> menus = roleMenuService.getMenuByRoleId(user.getRole());
        return new LoginUser(user, menus);
    }
}
