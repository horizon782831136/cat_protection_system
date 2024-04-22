package com.liuwei.admin.controller;

import com.liuwei.admin.domain.dto.add.FollowersAddDTO;
import com.liuwei.admin.domain.dto.update.FollowersUpdateDTO;
import com.liuwei.admin.service.FollowersService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Followers;
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
 * (Followers)表控制层
 *
 * @author makejava
 * @since 2024-02-10 19:57:22
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("followers")
public class FollowersController {
   private final FollowersService followersService;

   @ApiOperation("条件查询")
   @ApiImplicitParams({
           @ApiImplicitParam(name = "current", value = "当前页"),
           @ApiImplicitParam(name = "size", value = "每页条数"),
           @ApiImplicitParam(name = "followersId", value = "关注者id"),
           @ApiImplicitParam(name = "followerId", value = "被关注者id"),
           @ApiImplicitParam(name = "followeeId", value = "关注者id"),
           @ApiImplicitParam(name = "status", value = "关注状态")
   })
   @PreAuthorize("hasAuthority('system:followers:list')")
   @GetMapping("/list")
   public Result getFollowersByCondition(@RequestParam(value = "current", defaultValue = "1") Integer current,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size,
                                         @RequestParam(value = "followersId", required = false) Integer followersId,
                                         @RequestParam(value = "followerId", required = false) Integer followerId,
                                         @RequestParam(value = "followeeId", required = false) Integer followeeId,
                                         @RequestParam(value = "status", required = false) Integer status
                                         ){
      return followersService.getFollowersByCondition(current, size, followersId, followerId, followeeId, status);
   }

   @ApiOperation("添加关注")
   @PostMapping
   @PreAuthorize("hasAuthority('system:followers:add')")
   public Result addFollowers(@RequestBody FollowersAddDTO followersAddDTO){
      Followers followers = BeanUtils.copyBean(followersAddDTO, Followers.class).init();
      boolean save = followersService.save(followers);
      return ResultUtils.add(save);
   }

   @ApiOperation("修改关注")
   @PutMapping
   @PreAuthorize("hasAuthority('system:followers:update')")
   public Result updateFollowers(@RequestBody FollowersUpdateDTO followersUpdateDTO){
      Followers followers = BeanUtils.copyBean(followersUpdateDTO, Followers.class).alter();
      boolean update = followersService.updateById(followers);
      return ResultUtils.update(update);
   }

   @ApiOperation("删除关注")
   @PreAuthorize("hasAuthority('system:followers:delete')")
   @ApiImplicitParam(name = "followersId", value = "关注者id", required = true)
   @DeleteMapping("/{followersId}")
   public Result deleteFollowers(@PathVariable(value = "followersId") Long followersId){
      boolean delete = followersService.removeById(followersId);
      return ResultUtils.delete(delete);
   }

   @ApiOperation("批量删除")
   @PreAuthorize("hasAuthority('system:followers:delete')")
   @ApiImplicitParam(name = "followersIds", value = "关注者id", required = true)
   @DeleteMapping("/batchDelete")
   public Result deleteFollowers(@RequestParam("ids") List<Long> ids){
      boolean delete = followersService.removeByIds(ids);
      return ResultUtils.delete(delete);
   }
}

