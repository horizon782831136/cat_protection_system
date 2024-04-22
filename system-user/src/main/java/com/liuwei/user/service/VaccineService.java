package com.liuwei.user.service;

import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Vaccine;
import com.liuwei.user.domain.dto.add.VaccineAddDTO;
import com.liuwei.user.domain.dto.update.VaccineUpdateDTO;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * (Vaccine)表服务接口
 *
 * @author makejava
 * @since 2024-01-02 14:37:07
 */
@Service
public interface VaccineService extends IService<Vaccine>{


    Result addVaccine(VaccineAddDTO vaccineAddDTO);

    Result updateVaccine(VaccineUpdateDTO vaccineUpdateDTO);

    Result getVaccineByAnimalId(Long animalId);

    Result getVaccineByUserId(Long userId);
}
