package com.liuwei.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liuwei.admin.domain.dto.add.RoleAddDTO;
import com.liuwei.admin.domain.dto.update.RoleUpdateDTO;
import com.liuwei.admin.domain.vo.RoleVO;
import com.liuwei.admin.service.RoleService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Role;
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
 * (Role)表控制层
 *
 * @author makejava
 * @since 2024-02-10 20:08:34
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("role")
public class RoleController {
   private final RoleService roleService;

   @ApiOperation("条件查询角色信息")
   @ApiImplicitParams({
           @ApiImplicitParam(name = "current", value = "当前页"),
           @ApiImplicitParam(name = "size", value = "每页条数"),
           @ApiImplicitParam(name = "roleId", value = "角色id"),
           @ApiImplicitParam(name = "roleName", value = "角色名称"),
           @ApiImplicitParam(name = "status", value = "角色状态")
   })
   @GetMapping("/list")
   @PreAuthorize("hasAuthority('system:role:list')")
   public Result getRoleByCondition(@RequestParam(value = "current", defaultValue = "1") Integer current,
                                    @RequestParam(value = "size", defaultValue = "10") Integer size,
                                    @RequestParam(value = "roleId", required = false) Long roleId,
                                    @RequestParam(value = "roleName", required = false) String roleName,
                                    @RequestParam(value = "status", required = false) Integer status
                                    )
   {
      return roleService.getRoleByCondition(current, size, roleId, roleName, status);
   }

   @ApiOperation("获取角色基本信息")
   @GetMapping("/base")
   @PreAuthorize("hasAuthority('system:role:list')")
   public Result getRoleBaseInfo()
   {
      LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
      queryWrapper.select(Role::getRoleId, Role::getRoleName);
      List<Role> list = roleService.list(queryWrapper);
      List<RoleVO> roleVOS = BeanUtils.copyList(list, RoleVO.class);
      return new Result(roleVOS);
   }


   @ApiOperation("添加角色")
   @PostMapping
   @PreAuthorize("hasAuthority('system:role:add')")
   public Result addRole(@RequestBody RoleAddDTO roleAddDTO)
   {
      Role role = BeanUtils.copyBean(roleAddDTO, Role.class).init();
      boolean save = roleService.save(role);
      return ResultUtils.add(save);
   }

   @ApiOperation("修改角色")
   @PutMapping
   @PreAuthorize("hasAuthority('system:role:update')")
   public Result updateRole(@RequestBody RoleUpdateDTO roleUpdateDTO)
   {
      Role role = BeanUtils.copyBean(roleUpdateDTO, Role.class).alter();
      boolean update = roleService.updateById(role);
      return ResultUtils.update(update);
   }

   @ApiOperation("删除角色")
   @PreAuthorize("hasAuthority('system:role:delete')")
   @ApiImplicitParam(name = "roleId", value = "角色id", required = true)
   @DeleteMapping("/{roleId}")
   public Result deleteRole(@PathVariable(value = "roleId") Long roleId)
   {
      boolean delete = roleService.removeById(roleId);
      return ResultUtils.delete(delete);
   }

   @ApiOperation("批量删除")
   @PreAuthorize("hasAuthority('system:role:delete')")
   @ApiImplicitParam(name = "roleIds", value = "角色id", required = true)
   @DeleteMapping("/batchDelete")
   public Result deleteRoles(@RequestParam("ids") List<Long> ids)
   {
      boolean delete = roleService.removeByIds(ids);
      return ResultUtils.delete(delete);
   }


}

