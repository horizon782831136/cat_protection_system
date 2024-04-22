package com.liuwei.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.dto.PageDTO;
import com.liuwei.framework.domain.po.Animal;
import com.liuwei.framework.domain.po.Category;
import com.liuwei.framework.domain.po.Image;
import com.liuwei.framework.enums.Status;
import com.liuwei.framework.utils.BeanUtils;
import com.liuwei.user.dao.AnimalDao;
import com.liuwei.user.domain.vo.AnimalVO;
import com.liuwei.user.service.AnimalService;
import com.liuwei.user.service.CategoryService;
import com.liuwei.user.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    private final CategoryService categoryService;
    private final ImageService imageService;
    @Override
    public Result getAllAnimal(Integer current, Integer size) {
        LambdaQueryWrapper<Animal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ge(Animal::getStatus, Status.NORMAL_STATUS);
        Page<Animal> page = new Page<>(current, size);
        animalDao.selectPage(page, queryWrapper);
        List<AnimalVO> collect = page.getRecords().stream().map(animal -> {
            AnimalVO animalVO = BeanUtils.copyBean(animal, AnimalVO.class);
            LambdaQueryWrapper<Category> categoryQueryWrapper = new LambdaQueryWrapper<>();
            categoryQueryWrapper.eq(Category::getCategoryId, animal.getCategoryId())
                    .select(Category::getName);
            Category one = categoryService.getOne(categoryQueryWrapper);
            animalVO.setCategory(one.getName());
            Image imageBasicInfo = imageService.getImageBasicInfo(animal.getImageId());
            animalVO.setPath(imageBasicInfo.getPath());
            return animalVO;
        }).collect(Collectors.toList());
        Page<AnimalVO> newPage = new Page<>(current, size);
        newPage.setRecords(collect).setTotal(page.getTotal());
        PageDTO<AnimalVO> of = PageDTO.of(newPage, AnimalVO.class);
        return new Result(of);
    }

    @Override
    public Result getAnimalByName(String name, Integer current, Integer size) {
        LambdaQueryWrapper<Animal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Animal::getName, name).
                ge(Animal::getStatus, Status.NORMAL_STATUS);
        Page<Animal> page = new Page<>(current, size);
        animalDao.selectPage(page, queryWrapper);
        List<AnimalVO> collect = page.getRecords().stream().map(animal -> {
            AnimalVO animalVO = BeanUtils.copyBean(animal, AnimalVO.class);
            LambdaQueryWrapper<Category> categoryQueryWrapper = new LambdaQueryWrapper<>();
            categoryQueryWrapper.eq(Category::getCategoryId, animal.getCategoryId())
                    .select(Category::getName);
            Category one = categoryService.getOne(categoryQueryWrapper);
            animalVO.setCategory(one.getName());
            Image imageBasicInfo = imageService.getImageBasicInfo(animal.getImageId());
            animalVO.setPath(imageBasicInfo.getPath());
            return animalVO;
        }).collect(Collectors.toList());
        Page<AnimalVO> newPage = new Page<>(current, size);
        newPage.setRecords(collect).setTotal(page.getTotal());
        PageDTO<AnimalVO> of = PageDTO.of(newPage, AnimalVO.class);
        return new Result(of);
    }

    @Override
    public Result getAnimalById(Long animalId) {
        LambdaQueryWrapper<Animal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Animal::getAnimalId, animalId).
                ge(Animal::getStatus, Status.NORMAL_STATUS);
        Animal animal = animalDao.selectOne(queryWrapper);
        AnimalVO animalVO = BeanUtils.copyBean(animal, AnimalVO.class);
        LambdaQueryWrapper<Category> categoryQueryWrapper = new LambdaQueryWrapper<>();
        categoryQueryWrapper.eq(Category::getCategoryId, animal.getCategoryId())
                .select(Category::getName);
        Category one = categoryService.getOne(categoryQueryWrapper);
        animalVO.setCategory(one.getName());
        Image imageBasicInfo = imageService.getImageBasicInfo(animal.getImageId());
        animalVO.setPath(imageBasicInfo.getPath());
        return new Result(animalVO);
    }

    @Override
    public Result getAnimalByCategoryId(Integer current, Integer size, Long categoryId) {
        LambdaQueryWrapper<Animal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Animal::getCategoryId, categoryId).
                ge(Animal::getStatus, Status.NORMAL_STATUS);
        Page<Animal> page = new Page<>(current, size);
        animalDao.selectPage(page, queryWrapper);
        List<AnimalVO> collect = page.getRecords().stream().map(animal -> {
            AnimalVO animalVO = BeanUtils.copyBean(animal, AnimalVO.class);
            LambdaQueryWrapper<Category> categoryQueryWrapper = new LambdaQueryWrapper<>();
            categoryQueryWrapper.eq(Category::getCategoryId, animal.getCategoryId())
                    .select(Category::getName);
            Category one = categoryService.getOne(categoryQueryWrapper);
            animalVO.setCategory(one.getName());
            Image imageBasicInfo = imageService.getImageBasicInfo(animal.getImageId());
            animalVO.setPath(imageBasicInfo.getPath());
            return animalVO;
        }).collect(Collectors.toList());
        Page<AnimalVO> newPage = new Page<>(current, size);
        newPage.setRecords(collect).setTotal(page.getTotal());
        PageDTO<AnimalVO> of = PageDTO.of(newPage, AnimalVO.class);
        return new Result(of);
    }
}
