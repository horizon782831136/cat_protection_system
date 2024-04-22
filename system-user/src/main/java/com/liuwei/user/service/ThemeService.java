package com.liuwei.user.service;

import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Theme;
import com.liuwei.user.domain.dto.add.ThemeAddDTO;
import com.liuwei.user.domain.dto.update.ThemeUpdateDTO;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * (Theme)表服务接口
 *
 * @author makejava
 * @since 2024-01-02 14:36:33
 */
@Service
public interface ThemeService extends IService<Theme>{


    Result getSystemTheme(Integer current, Integer size);

    Result getThemeByShareCode(String shareCode);

    Result getThemeByUserId(Long userId, Integer current, Integer size);

    Result addTheme(ThemeAddDTO themeAddDTO);

    Result updateTheme(ThemeUpdateDTO themeUpdateDTO);

    Result getCheckedThemeByUserId(Long userId);
}
