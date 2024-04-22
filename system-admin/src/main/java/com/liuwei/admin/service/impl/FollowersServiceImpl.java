package com.liuwei.admin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuwei.admin.dao.FollowersDao;
import com.liuwei.admin.domain.vo.FollowersVO;
import com.liuwei.admin.service.FollowersService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.dto.PageDTO;
import com.liuwei.framework.domain.po.Followers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


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

    @Override
    public Result getFollowersByCondition(Integer current, Integer size, Integer followersId, Integer followerId,
                                          Integer followeeId, Integer status) {
        LambdaQueryWrapper<Followers> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(ObjectUtil.isNotEmpty(followersId), Followers::getFollowersId, followersId)
                .like(ObjectUtil.isNotEmpty(followeeId), Followers::getFolloweeId, followeeId)
                .like(ObjectUtil.isNotEmpty(followerId), Followers::getFollowerId, followerId)
                .eq(ObjectUtil.isNotEmpty(status), Followers::getStatus, status)
                .orderByDesc(Followers::getCreateTime);
        Page<Followers> page = new Page<>(current, size);
        followersDao.selectPage(page, queryWrapper);
        PageDTO<FollowersVO> of = PageDTO.of(page, FollowersVO.class);
        return new Result(of);
    }
}
