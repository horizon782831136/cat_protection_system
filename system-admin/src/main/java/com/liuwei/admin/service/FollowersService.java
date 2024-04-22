package com.liuwei.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Followers;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * (Followers)表服务接口
 *
 * @author makejava
 * @since 2024-01-02 14:16:11
 */
@Service
public interface FollowersService extends IService<Followers>{
    public Result getFollowersByCondition(Integer current, Integer size, Integer followersId, Integer followerId,
                                          Integer followeeId, Integer status
    );
    

}
