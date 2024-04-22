package com.liuwei.user.controller;

import com.liuwei.framework.domain.Result;
import com.liuwei.framework.utils.ResultUtils;
import com.liuwei.user.domain.dto.add.ThemeAddDTO;
import com.liuwei.user.domain.dto.update.ThemeUpdateDTO;
import com.liuwei.user.service.ThemeService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    //查询系统主题基本信息
    @ApiOperation("查询系统主题基本信息")
    @GetMapping("/system")
    public Result getSystemTheme(@RequestParam(value = "current", defaultValue = "1") Integer current,
                                 @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        return themeService.getSystemTheme(current, size);
    }

    //根据分享码查询主题信息
    @ApiOperation("根据分享码查询主题信息")
    @ApiImplicitParam(name = "shareCode", value = "分享码", required = true)
    @GetMapping("/shareCode")
    public Result getThemeByShareCode(@RequestParam(value = "shareCode") String shareCode) {
        return themeService.getThemeByShareCode(shareCode);
    }

    //根据用户ID查询主题信息
    @ApiOperation("根据用户ID查询主题信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true),
            @ApiImplicitParam(name = "current", value = "当前页码", required = true),
            @ApiImplicitParam(name = "size", value = "每页条数", required = true)
    })
    @PreAuthorize("hasAuthority('front:theme:list')")
    @GetMapping("/userId")
    public Result getThemeByUserId(@RequestParam(value = "userId") Long userId,
                                   @RequestParam(value = "current", defaultValue = "1") Integer current,
                                   @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        return themeService.getThemeByUserId(userId, current, size);
    }

    //根据用户ID查询选中主题信息
    @ApiOperation("根据用户ID查询选中主题信息")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true)
    @GetMapping("/checked/{userId}")
    public Result getCheckedThemeByUserId(@PathVariable(value = "userId") Long userId) {
        return themeService.getCheckedThemeByUserId(userId);
    }

    @ApiOperation("添加主题")
    @PreAuthorize("hasAuthority('front:theme:add')")
    @PostMapping
    public Result addTheme(@RequestBody ThemeAddDTO themeAddDTO) {
        return themeService.addTheme(themeAddDTO);
    }

    @ApiOperation("修改主题")
    @PreAuthorize("hasAuthority('front:theme:update')")
    @PutMapping
    public Result updateTheme(@RequestBody ThemeUpdateDTO themeUpdateDTO) {
        return themeService.updateTheme(themeUpdateDTO);
    }

    @ApiOperation("删除主题")
    @PreAuthorize("hasAuthority('front:theme:delete')")
    @ApiImplicitParam(name = "themeId", value = "主题id", required = true)
    @DeleteMapping("/{themeId}")
    public Result deleteTheme(@PathVariable(value = "themeId") Long themeId) {
        boolean delete = themeService.removeById(themeId);
        return ResultUtils.delete(delete);
    }

}

