package com.liuwei.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Vaccine;
import org.springframework.stereotype.Service;

/**
 * (Vaccine)表服务接口
 *
 * @author makejava
 * @since 2024-01-02 14:37:07
 */
@Service
public interface VaccineService extends IService<Vaccine>{
    public Result getVaccineByCondition(Integer current, Integer size, Long vaccineId, Long animalId, Long userId,
            String type, Integer status
    );
    

}
