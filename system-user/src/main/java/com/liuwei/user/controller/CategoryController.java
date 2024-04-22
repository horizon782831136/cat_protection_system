package com.liuwei.user.controller;

import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Category;
import com.liuwei.framework.utils.BeanUtils;
import com.liuwei.user.domain.vo.CategoryVO;
import com.liuwei.user.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @ApiOperation("获取所有分类")
    @GetMapping("/all")
    public Result getAllCategory(){
        return categoryService.getAllCategory();
    }

    @ApiOperation("根据id获取分类")
    @GetMapping("/{categoryId}")
    public Result getCategoryById(@PathVariable(value = "categoryId") Long categoryId){
        Category category = categoryService.getById(categoryId);
        CategoryVO categoryVO = BeanUtils.copyBean(category, CategoryVO.class);
        return new Result(categoryVO);
    }

    @ApiOperation("查询根种类")
    @GetMapping("/root")
    public Result getRootCategory(){
       return categoryService.getRootCategory();
    }
}
