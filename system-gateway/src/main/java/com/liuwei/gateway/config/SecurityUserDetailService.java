package com.liuwei.gateway.config;

import cn.hutool.core.util.ObjectUtil;
import com.liuwei.api.SecurityClient;
import com.liuwei.api.UserClient;
import com.liuwei.framework.domain.po.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailService implements ReactiveUserDetailsService {
    private final UserClient userClient;
    private final SecurityClient securityClient;
    @Override
    public Mono<UserDetails> findByUsername(String username) {
        User user = userClient.findByUsername(username);
        if(ObjectUtil.isNotEmpty(user)){
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            securityClient.getMenuByRoleId(user.getRole()).forEach(menu -> {
                authorities.add(new SimpleGrantedAuthority(menu.getAuthority()));
            });
            return null;// todo 后续更新
        }
        return null; //后续抛异常
    }
}
