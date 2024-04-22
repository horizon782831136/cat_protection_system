package com.liuwei.user.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.dto.PageDTO;
import com.liuwei.framework.domain.po.Image;
import com.liuwei.framework.domain.po.Theme;
import com.liuwei.framework.enums.Cate;
import com.liuwei.framework.enums.Default;
import com.liuwei.framework.enums.Status;
import com.liuwei.framework.service.UniqueIdGeneratorService;
import com.liuwei.framework.utils.BeanUtils;
import com.liuwei.framework.utils.ResultUtils;
import com.liuwei.user.dao.ThemeDao;
import com.liuwei.user.domain.dto.add.ThemeAddDTO;
import com.liuwei.user.domain.dto.update.ThemeUpdateDTO;
import com.liuwei.user.domain.vo.ThemeUserVO;
import com.liuwei.user.domain.vo.ThemeVisitorVO;
import com.liuwei.user.service.ImageService;
import com.liuwei.user.service.ThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


/**
 * (Theme)表服务实现类
 *
 * @author makejava
 * @since 2024-01-02 14:36:33
 */
@Service
@RequiredArgsConstructor
public class ThemeServiceImpl extends ServiceImpl<ThemeDao,Theme> implements ThemeService {
    private final UniqueIdGeneratorService uniqueIdGeneratorService;
    private final ThemeDao themeDao;
    private final ImageService imageService;
    @Override
    public Result getSystemTheme(Integer current, Integer size) {
        LambdaQueryWrapper<Theme> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Theme::getUserId, Cate.SYSTEM.getKey())
                .eq(Theme::getStatus, Status.NORMAL_STATUS.getKey());
        Page<Theme> page = new Page<>(current, size);
        themeDao.selectPage(page, lambdaQueryWrapper);
        //将图片路径绑定到vo上
        List<ThemeVisitorVO> collect = page.getRecords().stream().map(theme -> {
            ThemeVisitorVO themeVisitorVO = BeanUtils.copyBean(theme, ThemeVisitorVO.class);
            //若存在图片
            if (ObjectUtil.isNotEmpty(theme.getImageId())) {
                Image imageBasicInfo = imageService.getImageBasicInfo(theme.getImageId());
                themeVisitorVO.setPath(imageBasicInfo.getPath());
            }
            return themeVisitorVO;
        }).collect(Collectors.toList());
        Page<ThemeVisitorVO> newPage = new Page<>(current, size);
        newPage.setRecords(collect).setTotal(page.getTotal());
        PageDTO<ThemeVisitorVO> of = PageDTO.of(newPage, ThemeVisitorVO.class);
        return new Result(of);
    }

    @Override
    public Result getThemeByShareCode(String shareCode) {
        LambdaQueryWrapper<Theme> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Theme::getShareCode, shareCode)
                .eq(Theme::getStatus, Status.NORMAL_STATUS.getKey());
        Theme theme = themeDao.selectOne(lambdaQueryWrapper);
        ThemeVisitorVO themeVisitorVO = BeanUtils.copyBean(theme, ThemeVisitorVO.class);
        if (ObjectUtil.isNotEmpty(theme.getImageId())) {
            Image imageBasicInfo = imageService.getImageBasicInfo(theme.getImageId());
            themeVisitorVO.setPath(imageBasicInfo.getPath());
        }
        return new Result(themeVisitorVO);
    }

    @Override
    public Result getThemeByUserId(Long userId, Integer current, Integer size) {
        LambdaQueryWrapper<Theme> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Theme::getUserId, userId)
                .orderByDesc(Theme::getCreateTime)
                .orderByDesc(Theme::getChecked);
        Page<Theme> page = new Page<>(current, size);
        themeDao.selectPage(page, lambdaQueryWrapper);
        //将图片路径绑定到vo上
        List<ThemeUserVO> collect = page.getRecords().stream().map(theme -> {
            ThemeUserVO themeUserVO = BeanUtils.copyBean(theme, ThemeUserVO.class);
            //若存在图片
            if (ObjectUtil.isNotEmpty(theme.getImageId())) {
                Image imageBasicInfo = imageService.getImageBasicInfo(theme.getImageId());
                themeUserVO.setPath(imageBasicInfo.getPath());
            }
            return themeUserVO;
        }).collect(Collectors.toList());
        Page<ThemeUserVO> newPage = new Page<>(current, size);
        newPage.setRecords(collect).setTotal(page.getTotal());
        PageDTO<ThemeVisitorVO> of = PageDTO.of(newPage, ThemeVisitorVO.class);
        return new Result(of);
    }

    @Override
    @Transactional
    public Result addTheme(ThemeAddDTO themeAddDTO) {
        Theme theme = BeanUtils.copyBean(themeAddDTO, Theme.class).init();
        if(themeAddDTO.getGenerateShareCode().equals(Short.valueOf(Default.GENERATE_SHARE_CODE.getKey()))){
            theme = theme.setShareCode(uniqueIdGeneratorService.generateUniqueId());
        }
        //若选中当前主题，则将之前选中的主题设为未选中
        if(theme.getChecked().equals(Status.CHECKED.getKey())){
            LambdaUpdateWrapper<Theme> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Theme::getUserId, theme.getUserId())
                    .eq(Theme::getChecked, Status.CHECKED.getKey())
            .set(Theme::getChecked, Status.UNCHECKED.getKey());
            themeDao.update(theme, updateWrapper);
        }
        boolean flag = themeDao.insert(theme) > 0;
        return ResultUtils.add(flag);
    }

    @Override
    public Result updateTheme(ThemeUpdateDTO themeUpdateDTO) {
        Theme theme = BeanUtils.copyBean(themeUpdateDTO, Theme.class).alter();
        if(themeUpdateDTO.getGenerateShareCode().equals(Short.valueOf(Default.GENERATE_SHARE_CODE.getKey()))){
            theme = theme.setShareCode(uniqueIdGeneratorService.generateUniqueId());
        }
        //若选中当前主题，则将之前选中的主题设为未选中
        if(theme.getChecked().equals(Status.CHECKED.getKey())){
            LambdaUpdateWrapper<Theme> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Theme::getUserId, theme.getUserId())
                    .eq(Theme::getChecked, Status.CHECKED.getKey())
                    .set(Theme::getChecked, Status.UNCHECKED.getKey());
            themeDao.update(theme, updateWrapper);
        }
        boolean flag = themeDao.updateById(theme) > 0;
        return ResultUtils.update(flag);
    }

    @Override
    public Result getCheckedThemeByUserId(Long userId) {
        LambdaQueryWrapper<Theme> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Theme::getUserId, userId)
                .eq(Theme::getChecked, Status.CHECKED.getKey());
        Theme theme = themeDao.selectOne(lambdaQueryWrapper);
        if(ObjectUtil.isEmpty(theme)){
            LambdaQueryWrapper<Theme> QueryWrapper = new LambdaQueryWrapper<>();
            QueryWrapper.eq(Theme::getUserId, Cate.SYSTEM.getKey())
                    .eq(Theme::getChecked, Status.CHECKED.getKey());
            theme = themeDao.selectOne(QueryWrapper);
        }
        ThemeVisitorVO themeVisitorVO = BeanUtils.copyBean(theme, ThemeVisitorVO.class);
        if (ObjectUtil.isNotEmpty(theme.getImageId())) {
            Image imageBasicInfo = imageService.getImageBasicInfo(theme.getImageId());
            themeVisitorVO.setPath(imageBasicInfo.getPath());
        }
        return new Result(themeVisitorVO);
    }
}
