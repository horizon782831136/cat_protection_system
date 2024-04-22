package com.liuwei.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Theme;
import org.springframework.stereotype.Service;

/**
 * (Theme)表服务接口
 *
 * @author makejava
 * @since 2024-01-02 14:36:33
 */
@Service
public interface ThemeService extends IService<Theme>{

    public Result getThemeByCondition(Integer current, Integer size, Long userId, String shareCode,
                                      String themeName, Long themeId, Integer checked, Integer status
    );

}
