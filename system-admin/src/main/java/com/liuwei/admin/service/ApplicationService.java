package com.liuwei.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Application;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * (Application)表服务接口
 *
 * @author makejava
 * @since 2024-01-02 13:45:25
 */
@Service
public interface ApplicationService extends IService<Application>{
    public Result getApplicationByCondition(Integer current, Integer size, Long applicationId, Long userId, Long referrer,
                                            Long administratorId, Integer type, Integer status
    );
    

}
