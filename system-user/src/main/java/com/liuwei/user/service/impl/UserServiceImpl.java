package com.liuwei.user.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.dto.LoginDTO;
import com.liuwei.framework.domain.dto.PageDTO;
import com.liuwei.framework.domain.po.Category;
import com.liuwei.framework.domain.po.Recommended;
import com.liuwei.framework.domain.po.User;
import com.liuwei.framework.enums.*;
import com.liuwei.framework.utils.*;
import com.liuwei.user.dao.UserDao;
import com.liuwei.user.domain.dto.LoginUser;
import com.liuwei.user.domain.dto.update.UserUpdateDTO;
import com.liuwei.user.domain.vo.UserBasicVO;
import com.liuwei.user.domain.vo.UserVO;
import com.liuwei.user.service.CategoryService;
import com.liuwei.user.service.ImageService;
import com.liuwei.user.service.RecommendedService;
import com.liuwei.user.service.UserService;
import com.liuwei.user.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2024-01-02 14:36:57
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserDao,User> implements UserService {
    private final UserDao userDao;
    private final ImageService imageService;
    private final RedisCache redisCache;
    private final CategoryService categoryService;
    private final RecommendedService recommendedService;
    private final AuthenticationManager authenticationManager;
    @Override
    public Result login(HttpServletRequest request, LoginDTO loginDTO) {
        //比较验证码、用户名、密码是否正确
        //1.比较验证码是否正确
        //1.1获取请求头中的token
        String token = request.getHeader("token");
        if(ObjectUtil.isEmpty(token)){
            return ResultUtils.error(Code.BUSINESS_ERROR.getKey(), Code.BUSINESS_ERROR.getMsg());
        }
        //1.2判断token是否过期
        //获取token对应的值
        String captcha = redisCache.getCacheObject(token);
        if(ObjectUtil.isEmpty(captcha)){
            return ResultUtils.error(Code.CAPTCHA_EXPIRED.getKey(), Code.CAPTCHA_EXPIRED.getMsg());
        }
        //1.3比较验证码是否正确
        if(!captcha.equals(loginDTO.getCaptcha())){
            //System.out.println(loginDTO.getCaptcha() + " : " + captcha + " : " + token + " 提交时");
            return ResultUtils.error(Code.CAPTCHA_ERROR.getKey(), Code.CAPTCHA_ERROR.getMsg());
        }
        Authentication authenticate;
        try{
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
            authenticate = authenticationManager.authenticate(authenticationToken);

        }catch(BadCredentialsException e){
            return ResultUtils.error(Code.LOGIN_ERROR.getKey(), Code.LOGIN_ERROR.getMsg());
        }

        //2.使用UserID生成Token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getUserId().toString();
        String jwt = JwtUtils.createJWT(userId);
        //3.把用户信息存入redis
        redisCache.setCacheObject("login:" + userId, loginUser);
        UserVO userVO = BeanUtils.copyBean(loginUser.getUser(), UserVO.class);
        if(ObjectUtil.isNotEmpty(loginUser.getUser().getAvatar())){
            userVO.setAvatar(imageService.getImageBasicInfo(loginUser.getUser().getAvatar()).getPath());
        }
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("user", userVO);
        userInfo.put("token", jwt);
        //登录成功
        //返回
        return new Result(Code.LOGIN_SUCCESS.getKey(), userInfo, Code.LOGIN_SUCCESS.getMsg());
    }

    @Override
    @Transactional
    public Result register(HttpServletRequest request, LoginDTO loginDTO) {
        //比较验证码、用户名、密码是否正确
        //1.比较验证码是否正确
        //1.1获取请求头中的token
        String token = request.getHeader("token");
        if(ObjectUtil.isEmpty(token)){
            return ResultUtils.error(Code.BUSINESS_ERROR.getKey(), Code.BUSINESS_ERROR.getMsg());
        }
        //1.2判断token是否过期
        //获取token对应的值
        String captcha = redisCache.getCacheObject(token);
        if(ObjectUtil.isEmpty(captcha)){
            return ResultUtils.error(Code.CAPTCHA_EXPIRED.getKey(), Code.CAPTCHA_EXPIRED.getMsg());
        }
        //1.3比较验证码是否正确
        if(!captcha.equals(loginDTO.getCaptcha())){
            //System.out.println(loginDTO.getCaptcha() + " : " + captcha + " : " + token + " 提交时");
            return ResultUtils.error(Code.CAPTCHA_ERROR.getKey(), Code.CAPTCHA_ERROR.getMsg());
        }
        //看用户名是否合法
        if(!ValidateUtils.validateUsername(loginDTO.getUsername())){
            return ResultUtils.error(Code.BUSINESS_ERROR.getKey(), Code.BUSINESS_ERROR.getMsg());
        }
        //看密码是否合法
        if(!ValidateUtils.validatePassword(loginDTO.getPassword())){
            return ResultUtils.error(Code.BUSINESS_ERROR.getKey(), Code.BUSINESS_ERROR.getMsg());
        }
        //2.判断用户名是否已存在
        if(userDao.selectCount(new LambdaQueryWrapper<User>().eq(User::getUsername, loginDTO.getUsername())) > 0){
            return ResultUtils.error(Code.USERNAME_EXIST.getKey(), Code.USERNAME_EXIST.getMsg());
        }
        User user = BeanUtils.copyBean(loginDTO, User.class);
        //加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //处理
        user.init().setRole(Cate.USER.getKey()).setNickname(user.getUsername()).setGender(Type.MALE.getKey());
        boolean flag = userDao.insert(user) > 0;
        if(flag){
            //获取所有种类
            LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Category::getStatus, Status.NORMAL_STATUS.getKey())
                    .select(Category::getCategoryId);
            List<Category> categoryList = categoryService.list(queryWrapper);
            List<Recommended> collect = categoryList.stream().map(category -> {
                Recommended recommended = new Recommended().init()
                        .setUserId(user.getUserId()).setCategoryId(category.getCategoryId())
                        .setCoefficient(InitValue.INIT_RECOMMEND_COFFICIENT)
                        .setScore(InitValue.INIT_RECOMMEND_SCORE);
                return recommended;
            }).collect(Collectors.toList());
            recommendedService.saveBatch(collect);
        }

        return ResultUtils.add(flag);
    }

    @Override
    public Result  getUserBasicInfo(Long userId) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserId,userId)
                .select(User::getUserId,User::getUsername,User::getDateOfBirth, User::getGender, User::getSignature,
                        User::getRole, User::getAvatar, User::getStatus, User::getCreateTime);
        User user = userDao.selectOne(queryWrapper);
        UserBasicVO userBasicVO = BeanUtils.copyBean(user, UserBasicVO.class);
        return new Result(userBasicVO);
    }

    @Override
    public Result getUserBasicInfoBySearch(String username, Integer current, Integer size) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(User::getUsername,username)
                .select(User::getUserId,User::getUsername,User::getDateOfBirth, User::getGender, User::getSignature,
                        User::getRole, User::getAvatar, User::getStatus, User::getCreateTime);
        Page<User> page = new Page<>(current,size);
        userDao.selectPage(page,queryWrapper);
        PageDTO<UserBasicVO> of = PageDTO.of(page, UserBasicVO.class);
        return new Result(of);
    }

    @Override
    public Result updateById(UserUpdateDTO userUpdateDTO) {
       //1.查看用户名是否符合规范
        String nickname = userUpdateDTO.getNickname();
        if(ObjectUtil.isNotEmpty(nickname) && !ValidateUtils.validateUsername(nickname)){
            ResultUtils.fail(Code.UPDATE_FAILED.getKey(), "用户名不符合规范");
        }
        //2.看密码是否符合规范
        String password = userUpdateDTO.getPassword();
        if(ObjectUtil.isNotEmpty(password) && !ValidateUtils.validatePassword(password)){
            ResultUtils.fail(Code.UPDATE_FAILED.getKey(), "密码不符合规范");
        }

        User user = BeanUtils.copyBean(userUpdateDTO, User.class).alter();
        if(ObjectUtil.isNotEmpty(user.getPassword())){
            user.setPassword(EncodeUtils.encode(user.getPassword()));
        }
        boolean flag = userDao.updateById(user) > 0;
        return ResultUtils.update(flag);
    }
}
