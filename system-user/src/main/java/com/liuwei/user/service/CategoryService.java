package com.liuwei.user.service;

import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Category;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * (Breed)表服务接口
 *
 * @author makejava
 * @since 2024-01-02 14:11:31
 */
@Service
public interface CategoryService extends IService<Category>{


    Result getAllCategory();

    Result getRootCategory();
}
