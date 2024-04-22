package com.liuwei.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Recommended;
import com.liuwei.framework.utils.BeanUtils;
import com.liuwei.user.dao.RecommendedDao;
import com.liuwei.user.domain.vo.RecommendedVO;
import com.liuwei.user.service.RecommendedService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


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
    public Result getRecommendedByUserId(Long userId) {
        //后续根据token获取用户id
        LambdaQueryWrapper<Recommended> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Recommended::getUserId,userId);
        List<Recommended> recommendeds = recommendedDao.selectList(lambdaQueryWrapper);
        RecommendedVO recommendedVO = BeanUtils.copyBean(recommendeds, RecommendedVO.class);
        return new Result(recommendedVO);
    }
}
