package com.liuwei.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Vaccine;
import com.liuwei.framework.enums.Status;
import com.liuwei.framework.utils.BeanUtils;
import com.liuwei.framework.utils.ResultUtils;
import com.liuwei.user.dao.VaccineDao;
import com.liuwei.user.domain.dto.add.VaccineAddDTO;
import com.liuwei.user.domain.dto.update.VaccineUpdateDTO;
import com.liuwei.user.domain.vo.VaccineUserVO;
import com.liuwei.user.domain.vo.VaccineVisitorVO;
import com.liuwei.user.service.VaccineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * (Vaccine)表服务实现类
 *
 * @author makejava
 * @since 2024-01-02 14:37:07
 */
@Service
@RequiredArgsConstructor
public class VaccineServiceImpl extends ServiceImpl<VaccineDao,Vaccine> implements VaccineService {
    private final VaccineDao vaccineDao;

    @Override
    public Result addVaccine(VaccineAddDTO vaccineAddDTO) {
        Vaccine vaccine = BeanUtils.copyBean(vaccineAddDTO, Vaccine.class).init()
                .setStatus(Status.NORMAL_STATUS.getKey());
        boolean save = vaccineDao.insert(vaccine) > 0;
        return ResultUtils.add(save);
    }

    @Override
    public Result updateVaccine(VaccineUpdateDTO vaccineUpdateDTO) {
        Vaccine vaccine = BeanUtils.copyBean(vaccineUpdateDTO, Vaccine.class).alter();
        boolean update = vaccineDao.updateById(vaccine) > 0;
        return ResultUtils.update(update);
    }

    @Override
    public Result getVaccineByAnimalId(Long animalId) {
        LambdaQueryWrapper<Vaccine> queryWrapper = new LambdaQueryWrapper<Vaccine>()
                .eq(Vaccine::getAnimalId, animalId)
                .eq(Vaccine::getStatus, Status.NORMAL_STATUS.getKey())
                .orderByDesc(Vaccine::getInoculationTime);
        List<Vaccine> vaccines = vaccineDao.selectList(queryWrapper);
        List<VaccineVisitorVO> vaccineVisitorVOS = BeanUtils.copyList(vaccines, VaccineVisitorVO.class);
        return new Result(vaccineVisitorVOS);
    }

    @Override
    public Result getVaccineByUserId(Long userId) {
        LambdaQueryWrapper<Vaccine> queryWrapper = new LambdaQueryWrapper<Vaccine>()
                .eq(Vaccine::getUserId, userId)
                .orderByDesc(Vaccine::getInoculationTime);
        List<Vaccine> vaccines = vaccineDao.selectList(queryWrapper);
        List<VaccineUserVO> vaccineUserVOS = BeanUtils.copyList(vaccines, VaccineUserVO.class);
        return new Result(vaccineUserVOS);
    }
}
