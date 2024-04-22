package com.liuwei.admin.controller;

import com.liuwei.admin.domain.dto.add.ApplicationAddDTO;
import com.liuwei.admin.domain.dto.update.ApplicationUpdateDTO;
import com.liuwei.admin.service.ApplicationService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Application;
import com.liuwei.framework.enums.Cate;
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


   @GetMapping("/list")
   @ApiOperation("申请条件查询")
   @PreAuthorize("hasAuthority('system:application:list')")
   @ApiImplicitParams({
           @ApiImplicitParam(name = "current", value = "当前页"),
           @ApiImplicitParam(name = "size", value = "每页条数"),
           @ApiImplicitParam(name = "applicationId", value = "申请id"),
           @ApiImplicitParam(name = "userId", value = "用户id"),
           @ApiImplicitParam(name = "referrer", value = "推荐人id"),
           @ApiImplicitParam(name = "administratorId", value = "管理员id"),
           @ApiImplicitParam(name = "type", value = "申请类型"),
           @ApiImplicitParam(name = "status", value = "申请状态")
   })
   public Result getApplicationByCondition(@RequestParam(value = "current", defaultValue = "1") Integer current,
                                           @RequestParam(value = "size", defaultValue = "10") Integer size,
                                           @RequestParam(value = "applicationId", required = false) Long applicationId,
                                           @RequestParam(value = "userId", required = false) Long userId,
                                           @RequestParam(value = "referrer", required = false) Long referrer,
                                           @RequestParam(value = "administratorId", required = false) Long administratorId,
                                           @RequestParam(value = "type", required = false) Integer type,
                                           @RequestParam(value = "status", required = false) Integer status
                                           ){
      return applicationService.getApplicationByCondition(current, size,
              applicationId, userId, referrer, administratorId, type, status);
   }


   @PostMapping
   @PreAuthorize("hasAuthority('system:application:add')")
   @ApiOperation("添加申请")
   public Result addApplication(@RequestBody ApplicationAddDTO applicationAddDTO){
      Application application = BeanUtils.copyBean(applicationAddDTO, Application.class).init();
      boolean save = applicationService.save(application);
      return ResultUtils.add(save);
   }

   @PutMapping
   @PreAuthorize("hasAuthority('system:application:update')")
   @ApiOperation("修改申请")
   public Result updateApplication(@RequestBody ApplicationUpdateDTO applicationUpdateDTO){
      Application application = BeanUtils.copyBean(applicationUpdateDTO, Application.class).alter()
              .setAdministratorId(Cate.SYSTEM.getKey());
      boolean update = applicationService.updateById(application);
      return ResultUtils.update(update);
   }
   @ApiOperation("删除申请")
   @PreAuthorize("hasAuthority('system:application:delete')")
   @ApiImplicitParam(name = "applicationId", value = "申请id", required = true)
   @DeleteMapping("/{applicationId}")
   public Result deleteApplication(@PathVariable(value = "applicationId") Long applicationId){
      boolean delete = applicationService.removeById(applicationId);
      return ResultUtils.delete(delete);
   }

   @ApiOperation("批量删除")
   @PreAuthorize("hasAuthority('system:application:delete')")
   @ApiImplicitParam(name = "applicationIds", value = "申请id", required = true)
   @DeleteMapping("/batchDelete")
   public Result deleteApplications(@RequestParam("ids") List<Long> ids){
      boolean delete = applicationService.removeByIds(ids);
      return ResultUtils.delete(delete);
   }
}

