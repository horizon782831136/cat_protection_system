package com.liuwei.admin.controller;

import com.liuwei.admin.domain.dto.add.RoleMenuAddDTO;
import com.liuwei.admin.domain.dto.update.RoleMenuUpdateDTO;
import com.liuwei.admin.service.RoleMenuService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.RoleMenu;
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
 * (RoleMenu)表控制层
 *
 * @author makejava
 * @since 2024-02-10 20:09:25
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("roleMenu")
public class RoleMenuController {
   private final RoleMenuService roleMenuService;

   @ApiOperation("条件查询角色绑定信息")
   @ApiImplicitParams({
           @ApiImplicitParam(name = "current", value = "当前页"),
           @ApiImplicitParam(name = "size", value = "每页条数"),
           @ApiImplicitParam(name = "id", value = "角色菜单id"),
           @ApiImplicitParam(name = "roleId", value = "角色id"),
           @ApiImplicitParam(name = "menuId", value = "菜单id"),
           @ApiImplicitParam(name = "status", value = "角色菜单状态")
   })
   @GetMapping("/list")
   @PreAuthorize("hasAuthority('system:roleMenu:list')")
   public Result getRoleMenuByCondition(@RequestParam(value = "current", defaultValue = "1") Integer current,
                                        @RequestParam(value = "size", defaultValue = "10") Integer size,
                                        @RequestParam(value = "id", required = false) Long id,
                                        @RequestParam(value = "roleId", required = false) Long roleId,
                                        @RequestParam(value = "menuId", required = false) Long menuId,
                                        @RequestParam(value = "status", required = false) Integer status
   )
   {
      return roleMenuService.getRoleMenuByCondition(current, size, id, roleId, menuId, status);
   }

   @ApiOperation("添加角色绑定信息")
   @PreAuthorize("hasAuthority('system:roleMenu:add')")
   @PostMapping
   public Result addRoleMenu(@RequestBody RoleMenuAddDTO roleMenuAddDTO)
   {
      RoleMenu roleMenu = BeanUtils.copyBean(roleMenuAddDTO, RoleMenu.class).init();
      boolean save = roleMenuService.save(roleMenu);
      return ResultUtils.add(save);
   }

   @ApiOperation("修改角色绑定信息")
   @PreAuthorize("hasAuthority('system:roleMenu:update')")
   @PutMapping
   public Result updateRoleMenu(@RequestBody RoleMenuUpdateDTO roleMenuUpdateDTO)
   {
      RoleMenu roleMenu = BeanUtils.copyBean(roleMenuUpdateDTO, RoleMenu.class).alter();
      boolean update = roleMenuService.updateById(roleMenu);
      return ResultUtils.update(update);
   }

   @ApiOperation("删除角色绑定信息")
   @PreAuthorize("hasAuthority('system:roleMenu:delete')")
   @ApiImplicitParam(name = "id", value = "角色菜单id", required = true)
   @DeleteMapping("/{id}")
   public Result deleteRoleMenu(@PathVariable(value = "id") Long id)
   {
      boolean delete = roleMenuService.removeById(id);
      return ResultUtils.delete(delete);
   }

   @ApiOperation("批量删除角色绑定信息")
   @PreAuthorize("hasAuthority('system:roleMenu:delete')")
   @ApiImplicitParam(name = "ids", value = "角色菜单id", required = true)
   @DeleteMapping("/batchDelete")
   public Result deleteRoleMenus(@RequestParam("ids") List<Long> ids)
   {
      boolean delete = roleMenuService.removeByIds(ids);
      return ResultUtils.delete(delete);
   }

}

