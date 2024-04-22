package com.liuwei.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.dto.LoginDTO;
import com.liuwei.framework.domain.po.User;
import com.liuwei.user.domain.dto.update.UserUpdateDTO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2024-01-02 14:36:57
 */
@Service
public interface UserService extends IService<User>{
    Result getUserBasicInfo(Long userId);
    Result getUserBasicInfoBySearch(String username, Integer current, Integer size);

    Result updateById(UserUpdateDTO userUpdateDTO);
    Result login(HttpServletRequest request, LoginDTO loginDTO);

    Result register(HttpServletRequest request, LoginDTO loginDTO);
}
