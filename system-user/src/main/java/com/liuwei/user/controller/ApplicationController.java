package com.liuwei.user.controller;


import com.liuwei.framework.domain.Result;
import com.liuwei.framework.utils.ResultUtils;
import com.liuwei.user.domain.dto.add.ApplicationAddDTO;
import com.liuwei.user.domain.dto.update.ApplicationUpdateDTO;
import com.liuwei.user.service.ApplicationService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * (Application)表控制层
 *
 * @author makejava
 * @since 2024-02-10 19:55:13
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("application")
public class ApplicationController {
   private final ApplicationService applicationService;

   //根据用户ID查询申请信息
   @ApiOperation("根据用户ID查询申请信息")
   @ApiImplicitParams({
           @ApiImplicitParam(name = "userId", value = "用户ID", required = true),
           @ApiImplicitParam(name = "current", value = "当前页数", defaultValue = "1"),
           @ApiImplicitParam(name = "size", value = "每页显示条数", defaultValue = "10")
   })
   @GetMapping("/userId")
   @PreAuthorize("hasAuthority('front:application:list')")
   public Result getApplicationByUserId(@RequestParam(value = "userId") Long userId,
                                        @RequestParam(value = "current", defaultValue = "1") Integer current,
                                        @RequestParam(value = "size", defaultValue = "10") Integer size
                                        ){
      return applicationService.getApplicationByUserId(userId, current, size);
   }

   @PostMapping
   @ApiOperation("添加申请")
   @PreAuthorize("hasAuthority('front:application:add')")
   public Result addApplication(@RequestBody ApplicationAddDTO applicationAddDTO){
      return applicationService.addApplication(applicationAddDTO);
   }

   @PutMapping
   @ApiOperation("修改申请")
   @PreAuthorize("hasAuthority('front:application:update')")
   public Result updateApplication(@RequestBody ApplicationUpdateDTO applicationUpdateDTO){
      return applicationService.updateApplication(applicationUpdateDTO);
   }
   @ApiOperation("删除申请")
   @PreAuthorize("hasAuthority('front:application:delete')")
   @ApiImplicitParam(name = "applicationId", value = "申请id", required = true)
   @DeleteMapping("/{applicationId}")
   public Result deleteApplication(@PathVariable(value = "applicationId") Long applicationId){
      boolean delete = applicationService.removeById(applicationId);
      return ResultUtils.delete(delete);
   }

}

