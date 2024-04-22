package com.liuwei.admin.controller;

import cn.hutool.core.util.ObjectUtil;
import com.liuwei.admin.domain.dto.add.UserAddDTO;
import com.liuwei.admin.domain.dto.update.UserUpdateDTO;
import com.liuwei.admin.service.UserService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.User;
import com.liuwei.framework.utils.BeanUtils;
import com.liuwei.framework.utils.ResultUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    @ApiOperation("用户条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页"),
            @ApiImplicitParam(name = "size", value = "每页条数"),
            @ApiImplicitParam(name = "userId", value = "用户id"),
            @ApiImplicitParam(name = "username", value = "用户名"),
            @ApiImplicitParam(name = "name", value = "姓名"),
            @ApiImplicitParam(name = "phoneNumber", value = "手机号"),
            @ApiImplicitParam(name = "idNumber", value = "身份证号"),
            @ApiImplicitParam(name = "email", value = "电子邮箱"),
            @ApiImplicitParam(name = "status", value = "用户状态")
    })
    @PreAuthorize("hasAuthority('system:user:list')")
    @GetMapping("/list")
    public Result getUserByCondition(@RequestParam(value = "current", defaultValue = "1") Integer current,
                                     @RequestParam(value = "size" ,defaultValue = "10") Integer size,
                                     @RequestParam(value = "userId", required = false) Long userId,
                                     @RequestParam(value = "username", required = false) String username,
                                     @RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
                                     @RequestParam(value = "idNumber", required = false) Integer idNumber,
                                     @RequestParam(value = "email", required = false) String email,
                                     @RequestParam(value = "status", required = false) Integer status
    ){
        return userService.getUserByCondition(current, size, userId, username, name, phoneNumber,
                idNumber, email, status);
    }


    @ApiOperation("添加用户")
    @PreAuthorize("hasAuthority('system:user:add')")
    @PostMapping
    public Result addUser(@RequestBody UserAddDTO userAddDTO){
        return userService.addUser(userAddDTO);
    }
    @ApiOperation("修改用户")
    @PreAuthorize("hasAuthority('system:user:update')")
    @PutMapping
    public Result updateUser(@RequestBody UserUpdateDTO userUpdateDTO){
        User user = BeanUtils.copyBean(userUpdateDTO, User.class).alter();
        if(ObjectUtil.isNotEmpty(user.getPassword())){
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        boolean flag = userService.updateById(user);
        return ResultUtils.update(flag);
    }

    @ApiOperation("删除用户")
    @PreAuthorize("hasAuthority('system:user:delete')")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    @DeleteMapping("/{userId}")
    public Result deleteUser(@PathVariable(value = "userId") Long userId){
        boolean flag = userService.removeById(userId);
        return ResultUtils.delete(flag);
    }

    @ApiOperation("批量删除")
    @PreAuthorize("hasAuthority('system:user:delete')")
    @ApiImplicitParam(name = "ids", value = "用户id", required = true)
    @DeleteMapping("/batchDelete")
    public Result deleteUsers(@RequestParam("ids") List<Long> userIds){
        boolean flag = userService.removeByIds(userIds);
        return ResultUtils.delete(flag);
    }

}
