package com.liuwei.user.service;

import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Animal;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * (Cat)表服务接口
 *
 * @author makejava
 * @since 2024-01-02 14:12:33
 */
@Service
public interface AnimalService extends IService<Animal>{


    Result getAllAnimal(Integer current, Integer size);

    Result getAnimalByName(String name, Integer current, Integer size);

    Result getAnimalById(Long animalId);

    Result getAnimalByCategoryId(Integer current, Integer size, Long categoryId);
}
