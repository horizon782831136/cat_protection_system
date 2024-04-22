package com.liuwei.admin.controller;

import com.liuwei.admin.domain.dto.add.VaccineAddDTO;
import com.liuwei.admin.domain.dto.update.VaccineUpdateDTO;
import com.liuwei.admin.service.VaccineService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Vaccine;
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
 * (Vaccine)表控制层
 *
 * @author makejava
 * @since 2024-02-10 20:13:21
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("vaccine")
public class VaccineController {
   private final VaccineService vaccineService;

   @ApiOperation("条件查询疫苗信息")
   @ApiImplicitParams({
           @ApiImplicitParam(name = "current", value = "当前页"),
           @ApiImplicitParam(name = "size", value = "每页条数"),
           @ApiImplicitParam(name = "vaccineId", value = "疫苗id"),
           @ApiImplicitParam(name = "animalId", value = "动物id"),
           @ApiImplicitParam(name = "userId", value = "用户id"),
           @ApiImplicitParam(name = "type", value = "疫苗类型"),
           @ApiImplicitParam(name = "status", value = "疫苗状态")
   })
   @GetMapping("/list")
   @PreAuthorize("hasAuthority('system:vaccine:list')")
   public Result getVaccineByCondition(
           @RequestParam(value = "current", defaultValue = "1") Integer current,
           @RequestParam(value = "size", defaultValue = "10") Integer size,
           @RequestParam(value = "vaccineId", required = false) Long vaccineId,
           @RequestParam(value = "animalId", required = false) Long animalId,
           @RequestParam(value = "userId", required = false) Long userId,
           @RequestParam(value = "type", required = false) String type,
           @RequestParam(value = "status", required = false) Integer status
   )
   {
     return vaccineService.getVaccineByCondition(current, size, vaccineId, animalId, userId, type, status);
   }

   @ApiOperation("添加疫苗")
   @PreAuthorize("hasAuthority('system:vaccine:add')")
   @PostMapping
   public Result addVaccine(@RequestBody VaccineAddDTO vaccineAddDTO)
   {
      Vaccine vaccine = BeanUtils.copyBean(vaccineAddDTO, Vaccine.class).init();
      boolean save = vaccineService.save(vaccine);
      return ResultUtils.add(save);
   }

   @ApiOperation("修改疫苗")
   @PreAuthorize("hasAuthority('system:vaccine:update')")
   @PutMapping
   public Result updateVaccine(@RequestBody VaccineUpdateDTO vaccineUpdateDTO)
   {
      Vaccine vaccine = BeanUtils.copyBean(vaccineUpdateDTO, Vaccine.class).alter();
      boolean update = vaccineService.updateById(vaccine);
      return ResultUtils.update(update);
   }

   @ApiOperation("删除疫苗")
   @PreAuthorize("hasAuthority('system:vaccine:delete')")
   @ApiImplicitParam(name = "vaccineId", value = "疫苗id", required = true)
   @DeleteMapping("/{vaccineId}")
   public Result deleteVaccine(@PathVariable(value = "vaccineId") Long vaccineId)
   {
      boolean delete = vaccineService.removeById(vaccineId);
      return ResultUtils.delete(delete);
   }

   @ApiOperation("批量删除疫苗")
   @PreAuthorize("hasAuthority('system:vaccine:delete')")
   @ApiImplicitParam(name = "vaccineIds", value = "疫苗id", required = true)
   @DeleteMapping("/batchDelete")
   public Result deleteVaccines(@RequestParam("ids") List<Long> ids)
   {
      boolean delete = vaccineService.removeByIds(ids);
      return ResultUtils.delete(delete);
   }
}

