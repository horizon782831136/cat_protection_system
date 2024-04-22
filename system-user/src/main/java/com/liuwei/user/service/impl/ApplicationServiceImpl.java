package com.liuwei.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.dto.PageDTO;
import com.liuwei.framework.domain.po.Animal;
import com.liuwei.framework.domain.po.Application;
import com.liuwei.framework.utils.BeanUtils;
import com.liuwei.framework.utils.ResultUtils;
import com.liuwei.user.dao.ApplicationDao;
import com.liuwei.user.domain.dto.add.ApplicationAddDTO;
import com.liuwei.user.domain.dto.update.ApplicationUpdateDTO;
import com.liuwei.user.domain.vo.AnimalVO;
import com.liuwei.user.domain.vo.ApplicationVO;
import com.liuwei.user.service.AnimalService;
import com.liuwei.user.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


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
    private final AnimalService animalService;
    private final ImageServiceImpl imageService;
    @Override
    public Result addApplication(ApplicationAddDTO applicationAddDTO) {
        Application application = BeanUtils.copyBean(applicationAddDTO, Application.class).init()
                .setDetail(JSON.toJSONString(applicationAddDTO.getAnimalId()));
        boolean flag = applicationDao.insert(application) > 0;
        return ResultUtils.add(flag);
    }

    @Override
    public Result getApplicationByUserId(Long userId, Integer current, Integer size) {
        //todo 后续从后端直接获取用户ID
        LambdaQueryWrapper<Application> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Application::getUserId, userId)
                .orderByDesc(Application::getCreateTime);
        Page<Application> page = new Page<>(current, size);
        applicationDao.selectPage(page, lambdaQueryWrapper);
        List<ApplicationVO> collect = page.getRecords().stream().map(application -> {
            ApplicationVO applicationVO = BeanUtils.copyBean(application, ApplicationVO.class);
            Long animalId = JSON.parseObject(application.getDetail(), Long.class);
            Animal animal = animalService.getById(animalId);
            AnimalVO animalVO = BeanUtils.copyBean(animal, AnimalVO.class);
            applicationVO.setAnimal(animalVO);
            animalVO.setPath(imageService.getImageBasicInfo(animal.getImageId()).getPath());
            return applicationVO;
        }).collect(Collectors.toList());
        Page<ApplicationVO> newPage = new Page<>(current, size);
        newPage.setRecords(collect).setTotal(page.getTotal());
        PageDTO<ApplicationVO> of = PageDTO.of(newPage, ApplicationVO.class);
        return new Result(of);
    }

    @Override
    public Result updateApplication(ApplicationUpdateDTO applicationUpdateDTO) {
        //todo 后续用户ID从后端获取
        Application application = BeanUtils.copyBean(applicationUpdateDTO, Application.class).alter();
        boolean flag = applicationDao.updateById(application) > 0;
        return ResultUtils.update(flag);
    }
}
