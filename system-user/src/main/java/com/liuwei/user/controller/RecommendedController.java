package com.liuwei.user.controller;

import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Recommended;
import com.liuwei.framework.utils.BeanUtils;
import com.liuwei.framework.utils.ResultUtils;
import com.liuwei.user.domain.dto.update.RecommendedUpdateDTO;
import com.liuwei.user.service.RecommendedService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * (Recommended)表控制层
 *
 * @author makejava
 * @since 2024-02-10 20:07:26
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("recommended")
public class RecommendedController {
   private final RecommendedService recommendedService;

   @ApiOperation("根据用户ID查询推荐")
   @PreAuthorize("hasAuthority('front:recommended:list')")
   @GetMapping("/userId")
   public Result getRecommendedByUserId(@RequestParam(value = "userId") Long userId)
   {
      return recommendedService.getRecommendedByUserId(userId);
   }

   @ApiOperation("修改推荐")
   @PreAuthorize("hasAuthority('front:recommended:update')")
   @PutMapping
   public Result updateRecommended(@RequestBody RecommendedUpdateDTO recommendedUpdateDTO)
   {
      Recommended recommended = BeanUtils.copyBean(recommendedUpdateDTO, Recommended.class).alter();
      boolean update = recommendedService.updateById(recommended);
      return ResultUtils.update(update);
   }
   @PreAuthorize("hasAuthority('front:recommended:update')")
   @ApiOperation("通过多个推荐信息修改推荐")
   @PostMapping("/multi")
   public Result updateRecommended(@RequestBody List<RecommendedUpdateDTO> recommendedUpdateDTOList)
   {
      List<Recommended> recommendedList = BeanUtils.copyList(recommendedUpdateDTOList, Recommended.class);
      recommendedList.forEach(Recommended::alter);
      boolean update = recommendedService.updateBatchById(recommendedList);
      return ResultUtils.update(update);
   }

}

