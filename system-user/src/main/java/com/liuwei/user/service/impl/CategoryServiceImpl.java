package com.liuwei.user.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Image;
import com.liuwei.framework.enums.Cate;
import com.liuwei.framework.enums.Status;
import com.liuwei.framework.utils.BeanUtils;
import com.liuwei.user.dao.CategoryDao;
import com.liuwei.framework.domain.po.Category;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuwei.user.domain.vo.CategoryRootVO;
import com.liuwei.user.domain.vo.CategoryVO;
import com.liuwei.user.service.CategoryService;
import com.liuwei.user.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * (Breed)表服务实现类
 *
 * @author makejava
 * @since 2024-01-02 14:11:45
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {
    private final CategoryDao categoryDao;
    private final ImageService imageService;
    @Override
    public Result getAllCategory() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getParentId, Cate.TOP_CATEGORY.getKey())
                .select(Category::getCategoryId, Category::getName, Category::getDescription, Category::getImageId)
                .ge(Category::getStatus, Status.NORMAL_STATUS);
        List<CategoryRootVO> rootCategoryVOS = categoryDao.selectList(queryWrapper).stream().map(category -> {
            CategoryRootVO rootCategoryVO = BeanUtils.copyBean(category, CategoryRootVO.class);
            //获取图片路径
            Image imageBasicInfo = imageService.getImageBasicInfo(category.getImageId());
            rootCategoryVO.setPath(imageBasicInfo.getPath());
            LambdaQueryWrapper<Category> childrenQueryWrapper = new LambdaQueryWrapper<>();
            childrenQueryWrapper.eq(Category::getParentId, category.getCategoryId())
                    .select(Category::getCategoryId, Category::getName, Category::getDescription, Category::getImageId)
                    .ge(Category::getStatus, Status.NORMAL_STATUS);
            List<CategoryRootVO> collect = categoryDao.selectList(childrenQueryWrapper).stream().map(cate -> {
                CategoryRootVO childCategory = BeanUtils.copyBean(cate, CategoryRootVO.class);
                Image childImage = imageService.getImageBasicInfo(cate.getImageId());
                childCategory.setPath(childImage.getPath());
                return childCategory;
            }).collect(Collectors.toList());
            rootCategoryVO.setChildren(collect);
            return rootCategoryVO;
        }).collect(Collectors.toList());
        return new Result(rootCategoryVOS);
    }

    @Override
    public Result getRootCategory() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getParentId, Cate.TOP_CATEGORY.getKey())
                .select(Category::getCategoryId, Category::getName, Category::getDescription, Category::getImageId)
                .ge(Category::getStatus, Status.NORMAL_STATUS);
        List<Category> categories = categoryDao.selectList(queryWrapper);
        List<CategoryVO> collect = categories.stream().map(category -> {
            CategoryVO categoryVO = BeanUtils.copyBean(category, CategoryVO.class);
            if (ObjectUtil.isNotEmpty(category.getImageId())) {
                Image imageBasicInfo = imageService.getImageBasicInfo(category.getImageId());
                categoryVO.setPath(imageBasicInfo.getPath());
            }
            return categoryVO;
        }).collect(Collectors.toList());

        return new Result(collect);
    }
}
