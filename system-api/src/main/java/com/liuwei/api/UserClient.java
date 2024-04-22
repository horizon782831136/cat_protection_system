package com.liuwei.api;

import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.dto.LoginDTO;
import com.liuwei.framework.domain.po.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

@FeignClient("system-admin")
public interface UserClient {
    @PostMapping("/login")
    public Result login(HttpServletRequest request, @RequestBody LoginDTO loginDTO);

    @GetMapping("/login/findByUsername")
    public User findByUsername(String username);
}
