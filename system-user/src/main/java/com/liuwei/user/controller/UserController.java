package com.liuwei.user.controller;

import cn.hutool.core.util.ObjectUtil;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Image;
import com.liuwei.framework.domain.po.User;
import com.liuwei.framework.utils.BeanUtils;
import com.liuwei.framework.utils.RedisCache;
import com.liuwei.framework.utils.ResultUtils;
import com.liuwei.user.domain.dto.LoginUser;
import com.liuwei.user.domain.dto.update.UserUpdateDTO;
import com.liuwei.user.domain.vo.UserVO;
import com.liuwei.user.service.ImageService;
import com.liuwei.user.service.UserService;
import com.liuwei.user.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final ImageService imageService;
    private final RedisCache redisCache;
    //获取本人详细信息
    @ApiOperation("获取本人详细信息")
    @PreAuthorize("hasAuthority('front:user:list')")
    @GetMapping("/userInfo")
    public Result getUserInfo(HttpServletRequest request){
        //获取token
        String token = request.getHeader("Authorization");
        if(ObjectUtil.isEmpty(token)){
            return ResultUtils.add(false);
        }

        token = token.substring(7);
        //解析token
        String userid;
        //从session中获取用户信息
        try {
            Claims claims = JwtUtils.parseJWT(token);
            userid = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }
        //从redis中获取用户信息
        String redisKey = "login:" + userid;
        LoginUser loginUser = redisCache.getCacheObject(redisKey);
        User user = userService.getById(loginUser.getUser().getUserId());
        UserVO userVO = BeanUtils.copyBean(user, UserVO.class);
        Image imageBasicInfo = imageService.getImageBasicInfo(user.getAvatar());
        userVO.setAvatar(imageBasicInfo.getPath());
        return new Result(userVO);
    }

    //根据用户ID查询用户基本信息
    @ApiOperation("根据用户ID查询用户基本信息")
    @ApiImplicitParam(name = "userId", required = true)
    @GetMapping("/userId/{userId}")
    public Result getUserInfo(@PathVariable(value = "userId") Long userId){
        return userService.getUserBasicInfo(userId);
    }

    //根据用户名模糊查询用户基本信息
    @ApiOperation("根据用户名模糊查询用户基本信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true),
            @ApiImplicitParam(name = "current", value = "当前页数", defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "每页显示条数", defaultValue = "10")
    })
    @GetMapping("/search")
    public Result getUserBasicInfoBySearch(@RequestParam(value = "username") String username,
                              @RequestParam(value = "current", defaultValue = "1") Integer current,
                              @RequestParam(value = "size", defaultValue = "10") Integer size){
    return userService.getUserBasicInfoBySearch(username, current, size);
    }

    @ApiOperation("修改用户")
    @PreAuthorize("hasAuthority('front:user:update')")
    @PutMapping
    public Result updateUser(@RequestBody UserUpdateDTO userUpdateDTO){
        return userService.updateById(userUpdateDTO);
    }

}
