package com.liuwei.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Recommended;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * (Recommended)表服务接口
 *
 * @author makejava
 * @since 2024-01-02 14:35:57
 */
@Service
public interface RecommendedService extends IService<Recommended>{
    public Result getRecommendedByCondition(Integer current, Integer size, Long recommendedId, Long userId,
                                            Long categoryId, Integer status
    );
    

}
