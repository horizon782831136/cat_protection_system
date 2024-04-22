package com.liuwei.admin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuwei.admin.dao.ApplicationDao;
import com.liuwei.admin.domain.vo.ApplicationVO;
import com.liuwei.admin.service.ApplicationService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.dto.PageDTO;
import com.liuwei.framework.domain.po.Application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * (Application)表服务实现类
 *
 * @author makejava
 * @since 2024-01-02 13:53:52
 */
@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl extends ServiceImpl<ApplicationDao,Application> implements ApplicationService {
    private final ApplicationDao applicationDao;

    @Override
    public Result getApplicationByCondition(Integer current, Integer size, Long applicationId, Long userId,
                                            Long referrer, Long administratorId, Integer type, Integer status) {
        LambdaQueryWrapper<Application> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(ObjectUtil.isNotEmpty(applicationId),Application::getApplicationId, applicationId)
                .like(ObjectUtil.isNotEmpty(userId), Application::getUserId, userId)
                .like(ObjectUtil.isNotEmpty(referrer), Application::getReferrer, referrer)
                .like(ObjectUtil.isNotEmpty(administratorId), Application::getAdministratorId, administratorId)
                .eq(ObjectUtil.isNotEmpty(type), Application::getType, type)
                .eq(ObjectUtil.isNotEmpty(status), Application::getStatus, status)
                .orderByDesc(Application::getCreateTime);
        Page<Application> page = new Page<>(current, size);
        applicationDao.selectPage(page, queryWrapper);
        PageDTO<ApplicationVO> of = PageDTO.of(page, ApplicationVO.class);
        return new Result(of);
    }
}
