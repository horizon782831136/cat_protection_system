package com.liuwei.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liuwei.admin.domain.dto.add.MenuAddDTO;
import com.liuwei.admin.domain.dto.update.MenuUpdateDTO;
import com.liuwei.admin.domain.vo.MenuVO;
import com.liuwei.admin.service.MenuService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Menu;
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
 * (Menu)表控制层
 *
 * @author makejava
 * @since 2024-02-10 20:05:50
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("menu")
public class MenuController {
   private final MenuService menuService;

   @ApiOperation("条件查询权限")
   @ApiImplicitParams({
           @ApiImplicitParam(name = "current", value = "当前页"),
           @ApiImplicitParam(name = "size", value = "每页条数"),
           @ApiImplicitParam(name = "menuId", value = "菜单id"),
           @ApiImplicitParam(name = "menuName", value = "菜单名称"),
           @ApiImplicitParam(name = "status", value = "菜单状态")
   })
   @PreAuthorize("hasAuthority('system:menu:list')")
   @GetMapping("/list")
   public Result getMenuByCondition(@RequestParam(value = "current", defaultValue = "1") Integer current,
                                    @RequestParam(value = "size", defaultValue = "10") Integer size,
                                    @RequestParam(value = "menuId", required = false) Long menuId,
                                    @RequestParam(value = "menuName", required = false) String menuName,
                                    @RequestParam(value = "status", required = false) Integer status
                                    ){
      return menuService.getMenuByCondition(current, size, menuId, menuName, status);
   }

   @ApiOperation("获取权限基本信息")
   @GetMapping("/base")
   @PreAuthorize("hasAuthority('system:menu:list')")
   public Result getMenuBaseInfo(){
      LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
      queryWrapper.select(Menu::getMenuId, Menu::getMenuName);
      List<Menu> list = menuService.list(queryWrapper);
      List<MenuVO> rst = BeanUtils.copyList(list, MenuVO.class);
      return new Result(rst);
   }

   @ApiOperation("添加权限")
   @PreAuthorize("hasAuthority('system:menu:add')")
   @PostMapping
   public Result addMenu(@RequestBody MenuAddDTO menuAddDTO){
      Menu menu = BeanUtils.copyBean(menuAddDTO, Menu.class).init();
      boolean save = menuService.save(menu);
      return ResultUtils.add(save);
   }

   @ApiOperation("修改权限")
   @PreAuthorize("hasAuthority('system:menu:update')")
   @PutMapping
   public Result updateMenu(@RequestBody MenuUpdateDTO menuUpdateDTO){
      Menu menu = BeanUtils.copyBean(menuUpdateDTO, Menu.class).alter();
      boolean update = menuService.updateById(menu);
      return ResultUtils.update(update);
   }

   @ApiOperation("删除权限")
   @PreAuthorize("hasAuthority('system:menu:delete')")
   @ApiImplicitParam(name = "menuId", value = "权限id", required = true)
   @DeleteMapping("/{menuId}")
   public Result deleteMenu(@PathVariable(value = "menuId") Long menuId){
      boolean delete = menuService.removeById(menuId);
      return ResultUtils.delete(delete);
   }

   @ApiOperation("批量删除")
   @PreAuthorize("hasAuthority('system:menu:delete')")
   @ApiImplicitParam(name = "menuIds", value = "权限id", required = true)
   @DeleteMapping("/batchDelete")
   public Result deleteMenus(@RequestParam("ids") List<Long> ids){
      boolean delete = menuService.removeByIds(ids);
      return ResultUtils.delete(delete);
   }

}

