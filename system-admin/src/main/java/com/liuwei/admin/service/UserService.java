package com.liuwei.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuwei.admin.domain.dto.add.UserAddDTO;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.dto.LoginDTO;
import com.liuwei.framework.domain.po.User;
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
    Result login(HttpServletRequest request, LoginDTO loginDTO);

    Result getUserByCondition(Integer current, Integer size, Long userId, String username, String name,
                              String phoneNumber, Integer idNumber, String email, Integer status
    );

    User findByUserName(String username);

    Result addUser(UserAddDTO userAddDTO);
}
