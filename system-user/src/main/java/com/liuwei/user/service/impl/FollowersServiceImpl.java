package com.liuwei.user.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.dto.PageDTO;
import com.liuwei.framework.domain.po.Followers;
import com.liuwei.framework.domain.po.User;
import com.liuwei.framework.enums.Status;
import com.liuwei.framework.utils.BeanUtils;
import com.liuwei.framework.utils.ResultUtils;
import com.liuwei.user.dao.FollowersDao;
import com.liuwei.user.domain.dto.add.FollowersAddDTO;
import com.liuwei.user.domain.vo.FollowersVO;
import com.liuwei.user.service.FollowersService;
import com.liuwei.user.service.ImageService;
import com.liuwei.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * (Followers)表服务实现类
 *
 * @author makejava
 * @since 2024-01-02 14:16:11
 */
@Service
@RequiredArgsConstructor
public class FollowersServiceImpl extends ServiceImpl<FollowersDao,Followers> implements FollowersService {
    private final FollowersDao followersDao;
    private final UserService userService;
    private final ImageService imageService;
    @Override
    public Result getFollowersByFollowerId(Long followerId, Integer current, Integer size) {
        LambdaQueryWrapper<Followers> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Followers::getFollowerId, followerId)
                .ge(Followers::getStatus, Status.NORMAL_STATUS.getKey())
        .orderByDesc(Followers::getCreateTime);
        Page<Followers> page = new Page<>(current, size);
        followersDao.selectPage(page, lambdaQueryWrapper);
        List<FollowersVO> collect = page.getRecords().stream().map(followers -> {
            FollowersVO followersVO = BeanUtils.copyBean(followers, FollowersVO.class);
            LambdaQueryWrapper<User> followeeQueryWrapper = new LambdaQueryWrapper<>();
            followeeQueryWrapper.eq(User::getUserId, followers.getFolloweeId())
                    .select(User::getNickname, User::getAvatar, User::getSignature);
            User followee = userService.getOne(followeeQueryWrapper);
            followersVO.setNickname(followee.getNickname());
            followersVO.setSignature(followee.getSignature());
            followersVO.setAvatar(imageService.getImageBasicInfo(followee.getAvatar()).getPath());
            return followersVO;
        }).collect(Collectors.toList());
        Page<FollowersVO> newPage = new Page<>(current, size);
        newPage.setRecords(collect).setTotal(page.getTotal());
        PageDTO<FollowersVO> of = PageDTO.of(newPage, FollowersVO.class);
        return new Result(of);
    }

    @Override
    public Result getFollowerByFolloweeId(Long followedId, Integer current, Integer size) {
        LambdaQueryWrapper<Followers> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Followers::getFolloweeId, followedId)
                .ge(Followers::getStatus, Status.NORMAL_STATUS.getKey())
                .orderByDesc(Followers::getCreateTime);
        Page<Followers> page = new Page<>(current, size);
        followersDao.selectPage(page, lambdaQueryWrapper);
        List<FollowersVO> collect = page.getRecords().stream().map(followers -> {
            FollowersVO followersVO = BeanUtils.copyBean(followers, FollowersVO.class);
            LambdaQueryWrapper<User> followerQueryWrapper = new LambdaQueryWrapper<>();
            followerQueryWrapper.eq(User::getUserId, followers.getFollowerId())
                    .select(User::getNickname, User::getAvatar, User::getSignature);
            User follower = userService.getOne(followerQueryWrapper);
            followersVO.setNickname(follower.getNickname());
            followersVO.setSignature(follower.getSignature());
            followersVO.setAvatar(imageService.getImageBasicInfo(follower.getAvatar()).getPath());
            return followersVO;
        }).collect(Collectors.toList());
        Page<FollowersVO> newPage = new Page<>(current, size);
        newPage.setRecords(collect).setTotal(page.getTotal());
        PageDTO<FollowersVO> of = PageDTO.of(newPage, FollowersVO.class);
        return new Result(of);
    }

    @Override
    public Integer getFollowerCount(Long followerId) {
        LambdaQueryWrapper<Followers> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Followers::getFollowerId, followerId)
                .eq(Followers::getStatus, Status.PUBLICLY_VISIBLE);
        int count = followersDao.selectCount(lambdaQueryWrapper);
        return count;
    }

    @Override
    public Integer getFolloweeCount(Long followeeId) {
        LambdaQueryWrapper<Followers> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Followers::getFolloweeId, followeeId)
                .eq(Followers::getStatus, Status.PUBLICLY_VISIBLE);
        int count = followersDao.selectCount(lambdaQueryWrapper);
        return count;
    }

    @Override
    public Result addFollowers(FollowersAddDTO followersAddDTO) {
        LambdaQueryWrapper<Followers> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Followers::getFollowerId, followersAddDTO.getFollowerId())
                .eq(Followers::getFolloweeId, followersAddDTO.getFolloweeId())
                .select(Followers::getFollowersId, Followers::getStatus);
        Followers followers = followersDao.selectOne(lambdaQueryWrapper);
        if(ObjectUtil.isNotEmpty(followers)){
            if(followers.getStatus().equals(Status.NORMAL_STATUS.getKey())){
                return ResultUtils.add(true);
            }else{
                followers.setStatus(Status.NORMAL_STATUS.getKey());
                int flag = followersDao.updateById(followers);
                return ResultUtils.add(flag > 0);
            }
        }else{
            followers = BeanUtils.copyBean(followersAddDTO, Followers.class).init();
            int flag = followersDao.insert(followers);
            return ResultUtils.add(flag > 0);
        }

    }

    @Override
    public Result deleteFollowers(Long followersId) {
        //修改状态为禁用
        Followers followers = new Followers();
        followers.setFollowersId(followersId);
        followers.setStatus(Status.HALTED_STATUS.getKey());
        int flag = followersDao.updateById(followers);
        return ResultUtils.delete(flag > 0);
    }

    @Override
    public Result getAllFollowers(Long userId) {
        LambdaQueryWrapper<Followers> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Followers::getFolloweeId, userId)
                .ge(Followers::getStatus, Status.NORMAL_STATUS.getKey())
                .select(Followers::getFollowerId);
        List<Followers> followers = followersDao.selectList(lambdaQueryWrapper);
        List<FollowersVO> collect = followers.stream().map(follower -> {
            LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userLambdaQueryWrapper.eq(User::getUserId, follower.getFollowerId())
                    .select(User::getNickname, User::getUsername);
            User one = userService.getOne(userLambdaQueryWrapper);
            FollowersVO followersVO = new FollowersVO();
            followersVO.setUsername(one.getUsername());
            followersVO.setNickname(one.getNickname());
            followersVO.setFollowerId(follower.getFollowerId());
            return followersVO;
        }).collect(Collectors.toList());
        return new Result(collect);
    }
}
