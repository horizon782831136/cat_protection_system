package com.liuwei.admin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuwei.admin.dao.UserDao;
import com.liuwei.admin.domain.dto.LoginUser;
import com.liuwei.admin.domain.dto.add.UserAddDTO;
import com.liuwei.admin.domain.vo.UserVO;
import com.liuwei.admin.service.ImageService;
import com.liuwei.admin.service.RecommendedService;
import com.liuwei.admin.service.UserService;
import com.liuwei.admin.utils.JwtUtils;
import com.liuwei.admin.utils.MediaUtils;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.dto.LoginDTO;
import com.liuwei.framework.domain.dto.PageDTO;
import com.liuwei.framework.domain.po.Category;
import com.liuwei.framework.domain.po.Image;
import com.liuwei.framework.domain.po.Recommended;
import com.liuwei.framework.domain.po.User;
import com.liuwei.framework.enums.Code;
import com.liuwei.framework.enums.InitValue;
import com.liuwei.framework.enums.Status;
import com.liuwei.framework.utils.BeanUtils;
import com.liuwei.framework.utils.RedisCache;
import com.liuwei.framework.utils.ResultUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    private final CategoryServiceImpl categoryService;
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
        userVO.setAvatar(imageService.getImageBasicInfo(loginUser.getUser().getAvatar()).getPath());

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("user", userVO);
        userInfo.put("token", jwt);
        //登录成功
        //返回
        return new Result(Code.LOGIN_SUCCESS.getKey(), userInfo, Code.LOGIN_SUCCESS.getMsg());
    }

    @Override
    public Result getUserByCondition(Integer current, Integer size, Long userId, String username, String name, String phoneNumber, Integer idNumber, String email, Integer status) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(ObjectUtil.isNotEmpty(userId), User::getUserId, userId)
                .like(ObjectUtil.isNotEmpty(username), User::getUsername, StringUtils.trimAllWhitespace(username))
                .like(ObjectUtil.isNotEmpty(name), User::getName, StringUtils.trimAllWhitespace(name))
                .like(ObjectUtil.isNotEmpty(phoneNumber), User::getPhoneNumber, phoneNumber)
                .like(ObjectUtil.isNotEmpty(idNumber), User::getIdNumber, idNumber)
                .like(ObjectUtil.isNotEmpty(email), User::getEmail, StringUtils.trimAllWhitespace(email))
                .eq(ObjectUtil.isNotEmpty(status), User::getStatus, status)
                .orderByDesc(User::getCreateTime);
        Page<User> page = new Page<>(current, size);
        userDao.selectPage(page, queryWrapper);
        List<UserVO> collection = page.getRecords().stream().map(user -> {
            UserVO userVO = BeanUtils.copyBean(user, UserVO.class);
            if(ObjectUtil.isNotEmpty(user.getAvatar())){
                Image image = imageService.getImageBasicInfo(user.getAvatar());
                try{
                    MediaUtils<Image> mediaUtils = new MediaUtils<>();
                    if(mediaUtils.dataValidate(image)){
                        image = mediaUtils.updateDataPath(image).alter();
                        imageService.updateById(image);
                    }

                } catch (Exception e){
                    e.printStackTrace();
                }

                //最后
                userVO.setAvatar(image.getPath());
            }
            return userVO;

        }).collect(Collectors.toList());
        Page<UserVO> newPage = new Page<>(current, size);
        newPage.setRecords(collection).setTotal(page.getTotal()).setPages(page.getPages());
        PageDTO<UserVO> of = PageDTO.of(newPage, UserVO.class);
        return new Result(of);
    }

    @Override
    public User findByUserName(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User user = userDao.selectOne(queryWrapper);
        return user;
    }

    @Override
    public Result addUser(UserAddDTO userAddDTO) {
        User user = BeanUtils.copyBean(userAddDTO, User.class).init();
        //先判断用户名是否重复
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, user.getUsername());
        if(userDao.selectCount(queryWrapper) > 0){
            return new Result(Code.USERNAME_EXIST.getKey(), Code.USERNAME_EXIST.getMsg());
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        boolean flag = userDao.insert(user) > 0;
        if(flag){
            //添加推荐
            //获取所有种类
            LambdaQueryWrapper<Category> categoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
            categoryLambdaQueryWrapper.eq(Category::getStatus, Status.NORMAL_STATUS.getKey())
                    .select(Category::getCategoryId);
            List<Category> categoryList = categoryService.list(categoryLambdaQueryWrapper);
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
}
