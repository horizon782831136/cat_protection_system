package com.liuwei.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Animal;
import org.springframework.stereotype.Service;

/**
 * (Cat)表服务接口
 *
 * @author makejava
 * @since 2024-01-02 14:12:33
 */
@Service
public interface AnimalService extends IService<Animal>{
    public Result getAnimalByCondition(Integer current, Integer size, Long animalId, String name, Integer status);



}
