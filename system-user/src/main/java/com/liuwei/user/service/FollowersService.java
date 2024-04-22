package com.liuwei.user.service;

import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Followers;
import com.liuwei.user.domain.dto.add.FollowersAddDTO;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * (Followers)表服务接口
 *
 * @author makejava
 * @since 2024-01-02 14:16:11
 */
@Service
public interface FollowersService extends IService<Followers>{


    Result getFollowersByFollowerId(Long followerId, Integer current, Integer size);

    Result getFollowerByFolloweeId(Long followedId, Integer current, Integer size);

    Integer getFollowerCount(Long followerId);

    Integer getFolloweeCount(Long followeeId);

    Result addFollowers(FollowersAddDTO followersAddDTO);

    Result deleteFollowers(Long followersId);

    Result getAllFollowers(Long userId);
}
