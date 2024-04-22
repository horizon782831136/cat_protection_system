package com.liuwei.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liuwei.admin.domain.dto.add.CategoryAddDTO;
import com.liuwei.admin.domain.dto.update.CategoryUpdateDTO;
import com.liuwei.admin.domain.vo.CategoryVO;
import com.liuwei.admin.service.CategoryService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Category;
import com.liuwei.framework.enums.Cate;
import com.liuwei.framework.enums.Default;
import com.liuwei.framework.utils.BeanUtils;
import com.liuwei.framework.utils.ResultUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/list")
    @ApiOperation("分类条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页"),
            @ApiImplicitParam(name = "size", value = "每页条数"),
            @ApiImplicitParam(name = "categoryId", value = "分类id"),
            @ApiImplicitParam(name = "name", value = "分类名称"),
            @ApiImplicitParam(name = "parentId", value = "父级分类id"),
            @ApiImplicitParam(name = "status", value = "分类状态")
    })
    @PreAuthorize("hasAuthority('system:category:list')")
    public Result getCategoryByCondition(@RequestParam(value = "current", defaultValue = "1") Integer current,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size,
                                         @RequestParam(value = "categoryId", required = false) Long categoryId,
                                         @RequestParam(value = "name", required = false) String name,
                                         @RequestParam(value = "parentId", required = false) Long parentId,
                                         @RequestParam(value = "status", required = false) Integer status
                                        ){
        return categoryService.getCategoryByCondition(current, size, categoryId, name, parentId, status);
    }

    @ApiOperation("查询所有种类的猫")
    @GetMapping("/cat")
    @PreAuthorize("hasAuthority('system:category:list')")
    public Result getCategoryByCat(){
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getParentId, Long.valueOf(Default.CAT_PARENT.getKey()));
        queryWrapper.select(Category::getCategoryId, Category::getName);
        List<Category> list = categoryService.list(queryWrapper);
        List<CategoryVO> categoryVOS = BeanUtils.copyList(list, CategoryVO.class);
        return new Result(categoryVOS);
    }

    @ApiOperation("获取根类别")
    @GetMapping("/parent")
    @PreAuthorize("hasAuthority('system:category:list')")
    public Result getCategoryByParent(){
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getParentId, Cate.TOP_CATEGORY.getKey());
        queryWrapper.select(Category::getCategoryId, Category::getName);
        List<Category> list = categoryService.list(queryWrapper);
        List<CategoryVO> categoryVOS = BeanUtils.copyList(list, CategoryVO.class);
        return new Result(categoryVOS);
    }

    @ApiOperation("获取基本信息")
    @GetMapping("/base")
    @PreAuthorize("hasAuthority('system:category:list')")
    public Result getCategoryBaseInfo(){
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Category::getCategoryId, Category::getName);
        List<Category> list = categoryService.list(queryWrapper);
        List<CategoryVO> categoryVOS = BeanUtils.copyList(list, CategoryVO.class);
        return new Result(categoryVOS);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('system:category:add')")
    @ApiOperation("添加类别")
    public Result addCategory(@RequestBody CategoryAddDTO categoryAddDTO){
        Category category = BeanUtils.copyBean(categoryAddDTO, Category.class).init();
        boolean save = categoryService.save(category);
        return ResultUtils.add(save);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('system:category:update')")
    @ApiOperation("修改类别")
    public Result updateCategory(@RequestBody CategoryUpdateDTO categoryUpdateDTO){
        Category category = BeanUtils.copyBean(categoryUpdateDTO, Category.class).alter();
        boolean update = categoryService.updateById(category);
        return ResultUtils.update(update);
    }

    @DeleteMapping("/{categoryId}")
    @ApiImplicitParam(name = "categoryId", value = "类别id", required = true)
    @ApiOperation("删除类别")
    @PreAuthorize("hasAuthority('system:category:delete')")
    public Result deleteCategory(@PathVariable(value = "categoryId") Long categoryId){
        boolean delete = categoryService.removeById(categoryId);
        return ResultUtils.delete(delete);
    }

    @ApiOperation("批量删除")
    @PreAuthorize("hasAuthority('system:category:delete')")
    @ApiImplicitParam(name = "categoryIds", value = "类别id", required = true)
    @DeleteMapping("/batchDelete")
    public Result deleteCategories(@RequestParam("ids") List<Long> ids){
        boolean delete = categoryService.removeByIds(ids);
        return ResultUtils.delete(delete);
    }

}
