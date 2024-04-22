package com.liuwei.admin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuwei.admin.dao.RecommendedDao;
import com.liuwei.admin.domain.vo.RecommendedVO;
import com.liuwei.admin.service.RecommendedService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.dto.PageDTO;
import com.liuwei.framework.domain.po.Recommended;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * (Recommended)表服务实现类
 *
 * @author makejava
 * @since 2024-01-02 14:35:57
 */
@Service
@RequiredArgsConstructor
public class RecommendedServiceImpl extends ServiceImpl<RecommendedDao,Recommended> implements RecommendedService {
    private final RecommendedDao recommendedDao;

    @Override
    public Result getRecommendedByCondition(Integer current, Integer size, Long recommendedId, Long userId,
                                            Long categoryId, Integer status) {
        LambdaQueryWrapper<Recommended> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(ObjectUtil.isNotEmpty(recommendedId), Recommended::getRecommendedId, recommendedId)
                .like(ObjectUtil.isNotEmpty(userId), Recommended::getUserId, userId)
                .like(ObjectUtil.isNotEmpty(categoryId), Recommended::getCategoryId, categoryId)
                .eq(ObjectUtil.isNotEmpty(status), Recommended::getStatus, status)
                .orderByDesc(Recommended::getCreateTime);
        Page<Recommended> page = new Page<>(current, size);
        recommendedDao.selectPage(page, queryWrapper);
        PageDTO<RecommendedVO> of = PageDTO.of(page, RecommendedVO.class);
        return new Result(of);
    }
}
