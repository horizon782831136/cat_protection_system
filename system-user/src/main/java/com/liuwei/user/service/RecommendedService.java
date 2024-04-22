package com.liuwei.user.service;

import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Recommended;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * (Recommended)表服务接口
 *
 * @author makejava
 * @since 2024-01-02 14:35:57
 */
@Service
public interface RecommendedService extends IService<Recommended>{


    Result getRecommendedByUserId(Long userId);
}
