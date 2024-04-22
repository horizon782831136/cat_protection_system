package com.liuwei.user.service;

import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Application;
import com.liuwei.user.domain.dto.add.ApplicationAddDTO;
import com.liuwei.user.domain.dto.update.ApplicationUpdateDTO;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * (Application)表服务接口
 *
 * @author makejava
 * @since 2024-01-02 13:45:25
 */
@Service
public interface ApplicationService extends IService<Application>{


    Result addApplication(ApplicationAddDTO applicationAddDTO);

    Result getApplicationByUserId(Long userId, Integer current, Integer size);

    Result updateApplication(ApplicationUpdateDTO applicationUpdateDTO);
}
