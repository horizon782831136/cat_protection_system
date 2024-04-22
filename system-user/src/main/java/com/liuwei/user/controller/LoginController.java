package com.liuwei.user.controller;


import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.dto.LoginDTO;
import com.liuwei.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/register")
    public Result register(HttpServletRequest request, @RequestBody LoginDTO loginDTO) {
        return userService.register(request, loginDTO);
    }


}
