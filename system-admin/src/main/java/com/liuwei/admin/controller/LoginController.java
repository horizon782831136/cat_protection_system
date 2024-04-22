package com.liuwei.admin.controller;

import com.liuwei.admin.service.UserService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.dto.LoginDTO;
import com.liuwei.framework.domain.po.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;

    @PostMapping
    public Result login(HttpServletRequest request, @RequestBody LoginDTO loginDTO) {
        return userService.login(request, loginDTO);
    }

    @GetMapping("/findByUsername")
    public User findByUsername(String username) {
        return userService.findByUserName(username);
    }
}
