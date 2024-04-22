package com.liuwei.user.controller;

import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Followers;
import com.liuwei.framework.utils.BeanUtils;
import com.liuwei.framework.utils.ResultUtils;
import com.liuwei.user.domain.dto.add.FollowersAddDTO;
import com.liuwei.user.domain.dto.update.FollowersUpdateDTO;
import com.liuwei.user.service.FollowersService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

   //根据关注者id查询关注
   @ApiOperation("根据关注者id查询关注")
   @ApiImplicitParams({
           @ApiImplicitParam(name = "followerId", value = "关注者id", required = true),
           @ApiImplicitParam(name = "current", value = "当前页数", defaultValue = "1"),
           @ApiImplicitParam(name = "size", value = "每页显示条数", defaultValue = "10")
   })
   @PreAuthorize("hasAuthority('front:followers:list')")
   @GetMapping("/followerId")
   public Result getFollowersByFollowerId(@RequestParam(value = "followerId") Long followerId,
                                          @RequestParam(value = "current", defaultValue = "1") Integer current,
                                          @RequestParam(value = "size", defaultValue = "10") Integer size
                                          ){
      return followersService.getFollowersByFollowerId(followerId, current, size);
   }

   //根据被关注者ID查询关注信息
   @PreAuthorize("hasAuthority('front:followers:list')")
   @ApiOperation("根据被关注者ID查询关注信息")
   @ApiImplicitParams({
           @ApiImplicitParam(name = "followeeId", value = "被关注者ID", required = true),
           @ApiImplicitParam(name = "current", value = "当前页数", defaultValue = "1"),
           @ApiImplicitParam(name = "size", value = "每页显示条数", defaultValue = "10")
   })
   @GetMapping("/followeeId")
   public Result getFollowerByFolloweeId(@RequestParam(value = "followeeId") Long followedId,
                                          @RequestParam(value = "current", defaultValue = "1") Integer current,
                                          @RequestParam(value = "size", defaultValue = "10") Integer size
                                          ){
      return followersService.getFollowerByFolloweeId(followedId, current, size);
   }

   //关注数
   @ApiOperation("根据关注者id查询关注数")
   @ApiImplicitParam(name = "followerId", value = "关注者id", required = true)
   @GetMapping("/followerCount/{followerId}")
   public Result getFollowerCount(@PathVariable(value = "followerId") Long followerId){
      Integer count = followersService.getFollowerCount(followerId);
      return new Result(count);
   }

   //粉丝数
   @ApiOperation("根据关注者id查询粉丝数")
   @ApiImplicitParam(name = "followee", value = "关注者id", required = true)
   @GetMapping("/followeeCount/{followeeId}")
   public Result getFolloweeCount(@PathVariable(value = "followeeId") Long followeeId){
      Integer count = followersService.getFolloweeCount(followeeId);
      return new Result(count);
   }
   @PreAuthorize("hasAuthority('front:followers:list')")

   @ApiOperation("获取全部粉丝")
   @GetMapping("/all/{userId}")
   public Result getAllFollowers(@PathVariable Long userId){
      return followersService.getAllFollowers(userId);
   }

   @ApiOperation("添加关注")
   @PreAuthorize("hasAuthority('front:followers:add')")
   @PostMapping
   public Result addFollowers(@RequestBody FollowersAddDTO followersAddDTO){
     return followersService.addFollowers(followersAddDTO);
   }

   @ApiOperation("修改关注")
   @PreAuthorize("hasAuthority('front:followers:update')")
   @PutMapping
   public Result updateFollowers(@RequestBody FollowersUpdateDTO followersUpdateDTO){
      Followers followers = BeanUtils.copyBean(followersUpdateDTO, Followers.class).alter();
      boolean update = followersService.updateById(followers);
      return ResultUtils.update(update);
   }

   @ApiOperation("删除关注")
   @PreAuthorize("hasAuthority('front:followers:delete')")
   @ApiImplicitParam(name = "followersId", value = "关注者id", required = true)
   @DeleteMapping("/{followersId}")
   public Result deleteFollowers(@PathVariable(value = "followersId") Long followersId){

      return followersService.deleteFollowers(followersId);
   }

}

