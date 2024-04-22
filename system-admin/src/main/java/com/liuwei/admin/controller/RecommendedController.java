package com.liuwei.admin.controller;

import com.liuwei.admin.domain.dto.add.RecommendedAddDTO;
import com.liuwei.admin.domain.dto.update.RecommendedUpdateDTO;
import com.liuwei.admin.service.RecommendedService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Recommended;
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

   @ApiOperation("条件查询推荐信息")
   @ApiImplicitParams({
           @ApiImplicitParam(name = "current", value = "当前页"),
           @ApiImplicitParam(name = "size", value = "每页条数"),
           @ApiImplicitParam(name = "recommendedId", value = "推荐id"),
           @ApiImplicitParam(name = "userId", value = "用户id"),
           @ApiImplicitParam(name = "categoryId", value = "种类id"),
           @ApiImplicitParam(name = "status", value = "推荐状态")
   })
   @PreAuthorize("hasAuthority('system:recommended:list')")
   @GetMapping("/list")
   public Result getRecommendedByCondition(@RequestParam(value = "current", defaultValue = "1") Integer current,
                                           @RequestParam(value = "size", defaultValue = "10") Integer size,
                                           @RequestParam(value = "recommendedId", required = false) Long recommendedId,
                                           @RequestParam(value = "userId", required = false) Long userId,
                                           @RequestParam(value = "categoryId", required = false) Long categoryId,
                                           @RequestParam(value = "status", required = false) Integer status
                                           ){
     return recommendedService.getRecommendedByCondition(current, size, recommendedId, userId, categoryId, status);
   }

   @ApiOperation("添加推荐")
   @PostMapping
   @PreAuthorize("hasAuthority('system:recommended:add')")
   public Result addRecommended(@RequestBody RecommendedAddDTO recommendedAddDTO)
   {
      Recommended recommended = BeanUtils.copyBean(recommendedAddDTO, Recommended.class).init();
      boolean save = recommendedService.save(recommended);
      return ResultUtils.add(save);
   }

   @ApiOperation("修改推荐")
   @PutMapping
   @PreAuthorize("hasAuthority('system:recommended:update')")
   public Result updateRecommended(@RequestBody RecommendedUpdateDTO recommendedUpdateDTO)
   {
      Recommended recommended = BeanUtils.copyBean(recommendedUpdateDTO, Recommended.class).alter();
      boolean update = recommendedService.updateById(recommended);
      return ResultUtils.update(update);
   }

   @ApiOperation("删除推荐")
   @PreAuthorize("hasAuthority('system:recommended:delete')")
   @ApiImplicitParam(name = "recommendedId", value = "推荐id", required = true)
   @DeleteMapping("/{recommendedId}")
   public Result deleteRecommended(@PathVariable(value = "recommendedId") Long recommendedId)
   {
      boolean delete = recommendedService.removeById(recommendedId);
      return ResultUtils.delete(delete);
   }

   @ApiOperation("批量删除")
   @PreAuthorize("hasAuthority('system:recommended:delete')")
   @ApiImplicitParam(name = "recommendedIds", value = "推荐id", required = true)
   @DeleteMapping("/batchDelete")
   public Result deleteRecommendeds(@RequestParam("ids") List<Long> ids)
   {
      boolean delete = recommendedService.removeByIds(ids);
      return ResultUtils.delete(delete);
   }
}

