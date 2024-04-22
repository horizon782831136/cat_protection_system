package com.liuwei.admin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuwei.admin.dao.VaccineDao;
import com.liuwei.admin.domain.vo.VaccineVO;
import com.liuwei.admin.service.ImageService;
import com.liuwei.admin.service.VaccineService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.dto.PageDTO;
import com.liuwei.framework.domain.po.Image;
import com.liuwei.framework.domain.po.Vaccine;
import com.liuwei.framework.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;


/**
 * (Vaccine)表服务实现类
 *
 * @author makejava
 * @since 2024-01-02 14:37:07
 */
@Service
@RequiredArgsConstructor
public class VaccineServiceImpl extends ServiceImpl<VaccineDao,Vaccine> implements VaccineService {
    private final VaccineDao vaccineDao;
    private final ImageService imageService;
    @Override
    public Result getVaccineByCondition(Integer current, Integer size, Long vaccineId, Long animalId, Long userId,
                                        String type, Integer status) {
        LambdaQueryWrapper<Vaccine> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(ObjectUtil.isNotEmpty(vaccineId), Vaccine::getVaccineId, vaccineId)
                .like(ObjectUtil.isNotEmpty(animalId), Vaccine::getAnimalId, animalId)
                .like(ObjectUtil.isNotEmpty(userId), Vaccine::getUserId, userId)
                .like(ObjectUtil.isNotEmpty(type), Vaccine::getType, StringUtils.trimAllWhitespace(type))
                .eq(ObjectUtil.isNotEmpty(status), Vaccine::getStatus, status)
                .orderByDesc(Vaccine::getCreateTime);
        Page<Vaccine> page = new Page<>(current, size);
        vaccineDao.selectPage(page, queryWrapper);

        List<VaccineVO> collect = page.getRecords().stream().map(vaccine -> {
            //1.先将可以转换的属性转换
            VaccineVO vaccineVO = BeanUtils.copyBean(vaccine, VaccineVO.class);
            //2.判断是否存在图片
            //2.1若存在图片，获取图片然后将图片的路径赋值给vaccineVO
            if (ObjectUtil.isNotEmpty(vaccine.getImageId())) {
                Image one = imageService.getImageBasicInfo(vaccine.getImageId());
                vaccineVO.setPath(one.getPath());
            }
            //3.返回vaccineVO
            return vaccineVO;
        }).collect(Collectors.toList());
        Page<VaccineVO> newPage = new Page(current, size);
        newPage.setRecords(collect).setTotal(page.getTotal()).setPages(page.getPages());
        PageDTO<VaccineVO> of = PageDTO.of(newPage, VaccineVO.class);
        return new Result(of);
    }
}
