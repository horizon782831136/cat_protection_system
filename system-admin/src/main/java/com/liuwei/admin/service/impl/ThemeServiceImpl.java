package com.liuwei.admin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuwei.admin.dao.ThemeDao;
import com.liuwei.admin.domain.vo.ThemeVO;
import com.liuwei.admin.service.ThemeService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.dto.PageDTO;
import com.liuwei.framework.domain.po.Theme;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * (Theme)表服务实现类
 *
 * @author makejava
 * @since 2024-01-02 14:36:33
 */
@Service
@RequiredArgsConstructor
public class ThemeServiceImpl extends ServiceImpl<ThemeDao, Theme> implements ThemeService {
    private final ThemeDao themeDao;

    @Override
    public Result getThemeByCondition(Integer current, Integer size, Long userId, String shareCode, String themeName,
                                      Long themeId, Integer checked, Integer status) {
        LambdaQueryWrapper<Theme> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(ObjectUtil.isNotEmpty(userId), Theme::getUserId, userId)
                .eq(ObjectUtil.isNotEmpty(shareCode), Theme::getShareCode, shareCode)
                .like(ObjectUtil.isNotEmpty(themeName), Theme::getThemeName, StringUtils.trimAllWhitespace(themeName))
                .like(ObjectUtil.isNotEmpty(themeId), Theme::getThemeId, themeId)
                .eq(ObjectUtil.isNotEmpty(checked), Theme::getChecked, checked)
                .eq(ObjectUtil.isNotEmpty(status), Theme::getStatus, status)
                .orderByDesc(Theme::getCreateTime);
        Page<Theme> page = new Page<>(current, size);
        themeDao.selectPage(page, queryWrapper);
        PageDTO<ThemeVO> of = PageDTO.of(page, ThemeVO.class);
        return new Result(of);
    }
}
