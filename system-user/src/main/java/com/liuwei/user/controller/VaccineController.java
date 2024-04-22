package com.liuwei.user.controller;

import com.liuwei.framework.domain.Result;
import com.liuwei.framework.utils.ResultUtils;
import com.liuwei.user.domain.dto.add.VaccineAddDTO;
import com.liuwei.user.domain.dto.update.VaccineUpdateDTO;
import com.liuwei.user.service.VaccineService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

   //根据猫咪id查询疫苗信息
   @ApiOperation("根据猫咪id查询疫苗信息")
   @ApiImplicitParam(name = "animalId", value = "猫咪id", required = true)
   @GetMapping("/animalId")
   public Result getVaccineByAnimalId(@RequestParam(value = "animalId") Long animalId)
   {
      return vaccineService.getVaccineByAnimalId(animalId);
   }

   //根据用户ID查询疫苗信息
   @ApiOperation("根据用户ID查询疫苗信息")
   @PreAuthorize("hasAuthority('front:vaccine:list')")
   @ApiImplicitParam(name = "userId", value = "用户ID", required = true)
   @GetMapping("/userId")
   public Result getVaccineByUserId(@RequestParam(value = "userId") Long userId)
   {
      return vaccineService.getVaccineByUserId(userId);
   }

   @ApiOperation("添加疫苗")
   @PreAuthorize("hasAuthority('front:vaccine:add')")
   @PostMapping
   public Result addVaccine(@RequestBody VaccineAddDTO vaccineAddDTO)
   {
      return vaccineService.addVaccine(vaccineAddDTO);
   }

   @ApiOperation("修改疫苗")
   @PreAuthorize("hasAuthority('front:vaccine:update')")
   @PutMapping
   public Result updateVaccine(@RequestBody VaccineUpdateDTO vaccineUpdateDTO)
   {
      return vaccineService.updateVaccine(vaccineUpdateDTO);
   }

   @ApiOperation("删除疫苗")
   @PreAuthorize("hasAuthority('front:vaccine:delete')")
   @ApiImplicitParam(name = "vaccineId", value = "疫苗id", required = true)
   @DeleteMapping("/{vaccineId}")
   public Result deleteVaccine(@PathVariable(value = "vaccineId") Long vaccineId)
   {
      boolean delete = vaccineService.removeById(vaccineId);
      return ResultUtils.delete(delete);
   }

}

