package com.liuwei.admin.controller;

import com.liuwei.admin.domain.dto.add.ThemeAddDTO;
import com.liuwei.admin.domain.dto.update.ThemeUpdateDTO;
import com.liuwei.admin.service.ThemeService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Theme;
import com.liuwei.framework.enums.Default;
import com.liuwei.framework.service.UniqueIdGeneratorService;
import com.liuwei.framework.utils.BeanUtils;
import com.liuwei.framework.utils.ResultUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * (Theme)表控制层
 *
 * @author makejava
 * @since 2024-02-10 20:09:48
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("theme")
public class ThemeController {
    private final ThemeService themeService;
    private final UniqueIdGeneratorService uniqueIdGeneratorService;
    @ApiOperation("条件查询主题信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页"),
            @ApiImplicitParam(name = "size", value = "每页条数"),
            @ApiImplicitParam(name = "userId", value = "用户id"),
            @ApiImplicitParam(name = "shareCode", value = "分享码"),
            @ApiImplicitParam(name = "themeName", value = "主题名称"),
            @ApiImplicitParam(name = "themeId", value = "主题id"),
            @ApiImplicitParam(name = "checked", value = "选中"),
            @ApiImplicitParam(name = "status", value = "主题状态")
    })
    @PreAuthorize("hasAuthority('system:theme:list')")
    @GetMapping("/list")
    public Result getThemeByCondition(
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "shareCode", required = false) String shareCode,
            @RequestParam(value = "themeName", required = false) String themeName,
            @RequestParam(value = "themeId", required = false) Long themeId,
            @RequestParam(value = "checked", required = false) Integer checked,
            @RequestParam(value = "status", required = false) Integer status
    ) {
       return themeService.getThemeByCondition(current, size, userId, shareCode, themeName, themeId, checked, status);
    }

    @ApiOperation("添加主题")
    @PreAuthorize("hasAuthority('system:theme:add')")
    @PostMapping
    public Result addTheme(@RequestBody ThemeAddDTO themeAddDTO) {
        Theme theme = BeanUtils.copyBean(themeAddDTO, Theme.class).init();
        if(themeAddDTO.getGenerateShareCode().equals(Short.valueOf(Default.GENERATE_SHARE_CODE.getKey()))){
            theme = theme.setShareCode(uniqueIdGeneratorService.generateUniqueId());
        }
        boolean save = themeService.save(theme);
        return ResultUtils.add(save);
    }

    @ApiOperation("修改主题")
    @PreAuthorize("hasAuthority('system:theme:update')")
    @PutMapping
    public Result updateTheme(@RequestBody ThemeUpdateDTO themeUpdateDTO) {
        Theme theme = BeanUtils.copyBean(themeUpdateDTO, Theme.class).alter();
        if(themeUpdateDTO.getGenerateShareCode().equals(Short.valueOf(Default.GENERATE_SHARE_CODE.getKey()))){
            theme = theme.setShareCode(uniqueIdGeneratorService.generateUniqueId());
        }
        boolean update = themeService.updateById(theme);
        return ResultUtils.update(update);
    }

    @ApiOperation("删除主题")
    @PreAuthorize("hasAuthority('system:theme:delete')")
    @ApiImplicitParam(name = "themeId", value = "主题id", required = true)
    @DeleteMapping("/{themeId}")
    public Result deleteTheme(@PathVariable(value = "themeId") Long themeId) {
        boolean delete = themeService.removeById(themeId);
        return ResultUtils.delete(delete);
    }

    @ApiOperation("批量删除主题")
    @PreAuthorize("hasAuthority('system:theme:delete')")
    @ApiImplicitParam(name = "themeIds", value = "主题id", required = true)
    @DeleteMapping("/batchDelete")
    public Result deleteThemes(@RequestParam("ids") List<Long> ids) {
        boolean delete = themeService.removeByIds(ids);
        return ResultUtils.delete(delete);
    }
}

