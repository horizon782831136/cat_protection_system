package com.liuwei.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Category;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Breed)表服务接口
 *
 * @author makejava
 * @since 2024-01-02 14:11:31
 */
@Service
public interface CategoryService extends IService<Category>{
    Result getCategoryByCondition(Integer current, Integer size, Long categoryId, String name,
                                         Long parentId, Integer status
    );

    boolean deleteCategory(Long categoryId);

    boolean deleteCategories(List<Long> ids);

}
