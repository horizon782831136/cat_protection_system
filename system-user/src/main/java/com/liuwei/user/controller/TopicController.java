package com.liuwei.user.controller;

import cn.hutool.core.util.ObjectUtil;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Topic;
import com.liuwei.framework.utils.BeanUtils;
import com.liuwei.framework.utils.RedisCache;
import com.liuwei.framework.utils.ResultUtils;
import com.liuwei.user.domain.dto.LoginUser;
import com.liuwei.user.domain.dto.add.CommentAddDTO;
import com.liuwei.user.domain.dto.add.TopicAddDTO;
import com.liuwei.user.domain.dto.update.TopicUpdateDTO;
import com.liuwei.user.service.TopicService;
import com.liuwei.user.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * (Topic)表控制层
 *
 * @author makejava
 * @since 2024-02-10 20:12:36
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("topic")
public class TopicController {
    private final TopicService topicService;
    private final RedisCache redisCache;

    //根据种类查询话题
    @ApiOperation("根据种类查询话题")
    @GetMapping("/category")
    public Result getTopicByCategoryId(@RequestParam(value = "categoryId") Long categoryId,
                                       @RequestParam(value = "current", defaultValue = "1") Integer current,
                                       @RequestParam(value = "size", defaultValue = "10") Integer size
                                       ) {
        return topicService.getTopicByCategoryId(categoryId, current, size);
    }
    //根据用户ID查询话题
    @ApiOperation("根据用户ID查询话题")
   @ApiImplicitParams({
           @ApiImplicitParam(name = "userId", value = "用户ID", required = true),
           @ApiImplicitParam(name = "current", value = "当前页码", defaultValue = "1"),
           @ApiImplicitParam(name = "size", value = "每页条数", defaultValue = "10")
   })
   @GetMapping("/userId")
   public Result getTopicByUserId(@RequestParam(value = "userId") Long userId,
                                  @RequestParam(value = "current", defaultValue = "1") Integer current,
                                  @RequestParam(value = "size", defaultValue = "10") Integer size
                                  ){
        return topicService.getTopicByUserId(userId, current, size);
    }

    //查询当前热门话题
    @ApiOperation("查询当前热门话题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页码", defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "每页条数", defaultValue = "10")
    })
   @GetMapping("/hot")

   public Result getHotTopic( @RequestParam(value = "current", defaultValue = "1") Integer current,
                              @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return topicService.getHotTopic(current, size);
    }

   //根据话题名模糊查询话题
    @ApiOperation("条件查询话题")
   @ApiImplicitParams({
           @ApiImplicitParam(name = "categoryId", value = "种类ID"),
           @ApiImplicitParam(name = "title", value = "话题名"),
           @ApiImplicitParam(name = "current", value = "当前页码", defaultValue = "1"),
           @ApiImplicitParam(name = "size", value = "每页条数", defaultValue = "10")
   })
   @GetMapping("/condition")
   public Result getTopicByCondition(
           @RequestParam(value = "categoryId", required = false) Long categoryId,
           @RequestParam(value = "title", required = false) String title,
                                  @RequestParam(value = "current", defaultValue = "1") Integer current,
                                  @RequestParam(value = "size", defaultValue = "10") Integer size
                                  ) {
        return topicService.getTopicByCondition(categoryId, title, current, size);
    }

    //根据用户推荐查询话题(可无用户)
    @ApiOperation("根据用户推荐查询话题")
   @ApiImplicitParams({
           @ApiImplicitParam(name = "userId", value = "用户ID"),//todo 表示系统， 后续用户id从后端获取
           @ApiImplicitParam(name = "current", value = "当前页码", defaultValue = "1"),
           @ApiImplicitParam(name = "size", value = "每页条数", defaultValue = "10")
   })
   @GetMapping("/recommend")
   public Result getTopictByRecommend(@RequestParam(value = "userId", required = false) Long userId,
                                     @RequestParam(value = "current", defaultValue = "1") Integer current,
                                     @RequestParam(value = "size", defaultValue = "10") Integer size
                                     ){
        return topicService.getTopicByRecommend(userId, current, size);
    }

    //根据用户ID查询评论
    @ApiOperation("根据用户ID查询评论")
    @PreAuthorize("hasAuthority('front:topic:list')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true),
            @ApiImplicitParam(name = "current", value = "当前页码", required = true),
            @ApiImplicitParam(name = "size", value = "每页条数", required = true)
    })
   @GetMapping("/comment/{userId}")
   public Result getCommentByUserId(@PathVariable(value = "userId") Long userId,
                                    @RequestParam(value = "current", defaultValue = "1") Integer current,
                                   @RequestParam(value = "size", defaultValue = "10") Integer size
   ) {
        return topicService.getCommentByUserId(userId, current, size);
    }


    //根据pid次查询评论
    @ApiOperation("根据pid次查询评论")
   @ApiImplicitParam(name = "topicId", value = "话题ID", required = true)
   @GetMapping("/comment/pid/{pid}")
   public Result getCommentByPid(@PathVariable(value = "pid") Long pid) {
        return topicService.getCommentByPid(pid);
    }
    //添加话题
    @ApiOperation("添加话题")
    @PostMapping
    @PreAuthorize("hasAuthority('front:topic:add')")
    public Result addTopic(HttpServletRequest request, @RequestBody TopicAddDTO topicAddDTO) {
        String token = request.getHeader("Authorization");
        if(ObjectUtil.isEmpty(token)){
            return ResultUtils.add(false);
        }

        token = token.substring(7);
        //解析token
        String userid;
        try {
            Claims claims = JwtUtils.parseJWT(token);
            userid = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }
        //从redis中获取用户信息
        String redisKey = "login:" + userid;
        LoginUser loginUser = redisCache.getCacheObject(redisKey);
        return topicService.addTopic(topicAddDTO, loginUser.getUser().getUserId());
    }


    @ApiOperation("添加评论")
    @PreAuthorize("hasAuthority('front:topic:add')")
    @PostMapping("/comment")
    public Result addComment(HttpServletRequest request, @RequestBody CommentAddDTO commentAddDTO) {
        String token = request.getHeader("Authorization");
        if(ObjectUtil.isEmpty(token)){
            return ResultUtils.add(false);
        }
        token = token.substring(7);
        //解析token
        String userid;
        //从session中获取用户信息
        try {
            Claims claims = JwtUtils.parseJWT(token);
            userid = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }
        //从redis中获取用户信息
        String redisKey = "login:" + userid;
        LoginUser loginUser = redisCache.getCacheObject(redisKey);
        return topicService.addComment(commentAddDTO, loginUser.getUser().getUserId());
    }

    @ApiOperation("点赞")
    @PreAuthorize("hasAuthority('front:topic:update')")
    @ApiImplicitParam(name = "topicId", value = "话题ID", required = true)
    @PutMapping("/like/{topicId}")
    public Result like(@PathVariable(value = "topicId") Long topicId){
        return topicService.like(topicId);
    }

    @ApiOperation("取消点赞")
    @PreAuthorize("hasAuthority('front:topic:update')")
    @ApiImplicitParam(name = "topicId", value = "话题ID", required = true)
    @PutMapping("/unlike/{topicId}")
    public Result unlike(@PathVariable(value = "topicId") Long topicId){
        return topicService.unlike(topicId);
    }

    @ApiOperation("点击")
    @PreAuthorize("hasAuthority('front:topic:update')")
    @ApiImplicitParam(name = "topicId", value = "话题ID", required = true)
    @PutMapping("/click/{topicId}")
    public Result click(@PathVariable(value = "topicId") Long topicId){
        return topicService.click(topicId);
    }

    @ApiOperation("修改话题")
    @PutMapping
    @PreAuthorize("hasAuthority('front:topic:update')")
    public Result updateTopic(@RequestBody TopicUpdateDTO topicUpdateDTO) {
        Topic topic = BeanUtils.copyBean(topicUpdateDTO, Topic.class).alter();
        boolean updateById = topicService.updateById(topic);
        return ResultUtils.update(updateById);
    }

    @ApiOperation("删除话题/评论")
    @PreAuthorize("hasAuthority('front:topic:delete')")
    @ApiImplicitParam(name = "topicId", value = "话题id", required = true)
    @DeleteMapping("/{topicId}")
    public Result deleteTopic(@PathVariable(value = "topicId") Long topicId) {
        boolean deleteById = topicService.removeById(topicId);
        return ResultUtils.delete(deleteById);
    }
}

