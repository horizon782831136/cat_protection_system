package com.liuwei.admin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuwei.admin.dao.AnimalDao;
import com.liuwei.admin.domain.vo.AnimalVO;
import com.liuwei.admin.service.AnimalService;
import com.liuwei.admin.service.ImageService;
import com.liuwei.admin.utils.MediaUtils;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.dto.PageDTO;
import com.liuwei.framework.domain.po.Animal;
import com.liuwei.framework.domain.po.Image;
import com.liuwei.framework.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;


/**
 * (Cat)表服务实现类
 *
 * @author makejava
 * @since 2024-01-02 14:12:33
 */
@Service
@RequiredArgsConstructor
public class AnimalServiceImpl extends ServiceImpl<AnimalDao, Animal> implements AnimalService {
    private final AnimalDao animalDao;
    private final ImageService imageService;

    @Override
    public Result getAnimalByCondition(Integer current, Integer size, Long animalId, String name, Integer status) {
        LambdaQueryWrapper<Animal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(ObjectUtil.isNotEmpty(animalId), Animal::getAnimalId, animalId)
                .like(ObjectUtil.isNotEmpty(name), Animal::getName, StringUtils.trimAllWhitespace(name)) //去除空格
                .ge(ObjectUtil.isNotEmpty(status), Animal::getStatus, status)
                .orderByDesc(Animal::getCreateTime);
        Page<Animal> page = new Page<>(current, size);
        animalDao.selectPage(page, queryWrapper);

        List<AnimalVO> collect = page.getRecords().stream().map(animal -> {
            AnimalVO animalVO = BeanUtils.copyBean(animal, AnimalVO.class);
            if (ObjectUtil.isNotEmpty(animal.getImageId())) {
                LambdaQueryWrapper<Image> imageQueryWrapper = new LambdaQueryWrapper<>();
                imageQueryWrapper.eq(Image::getImageId, animal.getImageId())
                        .select(Image::getOriginalName,Image::getName, Image::getPath, Image::getCreateTime,
                                Image::getUpdateTime, Image::getImageId);
                Image image = imageService.getOne(imageQueryWrapper);
                try {
                    MediaUtils<Image> mediaUtils = new MediaUtils<>();
                    if(mediaUtils.dataValidate(image)){
                        image = mediaUtils.updateDataPath(image).alter();
                        imageService.updateById(image);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                animalVO.setAvatar(image.getPath());
            }
            return animalVO;
        }).collect(Collectors.toList());
        Page<AnimalVO> newPage = new Page<>(current, size);
        newPage.setRecords(collect).setTotal(page.getTotal()).setPages(page.getPages());
        PageDTO<AnimalVO> of = PageDTO.of(newPage, AnimalVO.class);
        return new Result(of);
    }



}
