package com.liuwei.admin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuwei.admin.dao.CategoryDao;
import com.liuwei.admin.domain.vo.CategoryVO;
import com.liuwei.admin.service.CategoryService;
import com.liuwei.admin.service.ImageService;
import com.liuwei.admin.utils.MediaUtils;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.dto.PageDTO;
import com.liuwei.framework.domain.po.Category;
import com.liuwei.framework.domain.po.Image;
import com.liuwei.framework.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
    public Result getCategoryByCondition(Integer current, Integer size, Long categoryId, String name, Long parentId, Integer status) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(ObjectUtil.isNotEmpty(categoryId), Category::getCategoryId, categoryId)
                .like(ObjectUtil.isNotEmpty(name), Category::getName, StringUtils.trimAllWhitespace(name))
                .like(ObjectUtil.isNotEmpty(parentId), Category::getParentId, parentId)
                .eq(ObjectUtil.isNotEmpty(status), Category::getStatus, status);
        Page<Category> page = new Page<>(current, size);
        categoryDao.selectPage(page, queryWrapper);
        //这里需要根据page中对象的图片id获取图片的路径，并将其重新封装到page中
        List<CategoryVO> collect = page.getRecords().stream().map(category -> {
            CategoryVO categoryVO = BeanUtils.copyBean(category, CategoryVO.class);
            if (ObjectUtil.isNotEmpty(category.getImageId())) {
                LambdaQueryWrapper<Image> imageQueryWrapper = new LambdaQueryWrapper<>();
                imageQueryWrapper.eq(Image::getImageId, category.getImageId())
                        .select(Image::getPath, Image::getOriginalName, Image::getName, Image::getCreateTime,
                                Image::getUpdateTime, Image::getImageId);
                Image image = imageService.getOne(imageQueryWrapper);

                try {
                    MediaUtils<Image> mediaUtils = new MediaUtils();
                    if(mediaUtils.dataValidate(image)){
                        image = mediaUtils.updateDataPath(image).alter();
                        imageService.updateById(image);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
                categoryVO.setAvatar(image.getPath());

            }
            return categoryVO;
        }).collect(Collectors.toList());
        Page<CategoryVO> newPage = new Page<>(current, size);
        newPage.setRecords(collect).setTotal(page.getTotal()).setPages(page.getPages());
        PageDTO<CategoryVO> of = PageDTO.of(newPage, CategoryVO.class);
        return new Result(of);
    }


    @Transactional
    @Override
    public boolean deleteCategory(Long categoryId) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Category::getImageId);
        Category category = categoryDao.selectOne(queryWrapper);
        boolean delete = categoryDao.deleteById(categoryId) > 0;
        if(delete)
            delete = imageService.deleteImage(category.getImageId());
        return delete;
    }
    @Transactional
    @Override
    public boolean deleteCategories(List<Long> ids) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Category::getImageId);
        List<Long> collects = categoryDao.selectList(queryWrapper).stream().map(Category::getImageId).collect(Collectors.toList());
        boolean flag = categoryDao.deleteBatchIds(ids) > 0;
        if(flag) flag = imageService.deleteImages(collects);
        return flag;
    }
}
