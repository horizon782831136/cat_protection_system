package com.liuwei.admin.controller;

import com.liuwei.admin.domain.dto.add.TopicAddDTO;
import com.liuwei.admin.domain.dto.update.TopicUpdateDTO;
import com.liuwei.admin.service.TopicService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Topic;
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

    @ApiOperation("条件查询话题信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页"),
            @ApiImplicitParam(name = "size", value = "每页条数"),
            @ApiImplicitParam(name = "topicId", value = "话题id"),
            @ApiImplicitParam(name = "userId", value = "用户id"),
            @ApiImplicitParam(name = "categoryId", value = "种类id"),
            @ApiImplicitParam(name = "title", value = "话题标题"),
            @ApiImplicitParam(name = "isTopic", value = "是话题"),
            @ApiImplicitParam(name = "parentId", value = "父种类id"),
            @ApiImplicitParam(name = "isTop", value = "是否置顶"),
            @ApiImplicitParam(name = "status", value = "话题状态")
    })
    @PreAuthorize("hasAuthority('system:topic:list')")
    @GetMapping("/list")
    public Result getTopicByCondition(
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "topicId", required = false) Long topicId,
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "isTopic", required = false) Integer isTopic,
            @RequestParam(value = "parentId", required = false) Long parentId,
            @RequestParam(value = "isTop", required = false) Integer isTop,
            @RequestParam(value = "status", required = false) Integer status
    ) {
        return topicService.getTopicByCondition(current, size, topicId, userId, categoryId, title, isTopic,
                parentId, isTop, status);
    }

    @ApiOperation("添加话题")
    @PreAuthorize("hasAuthority('system:topic:add')")
    @PostMapping
    public Result addTopic(@RequestBody TopicAddDTO topicAddDTO) {
        boolean save = topicService.addTopic(topicAddDTO);
        return ResultUtils.add(save);
    }

    @ApiOperation("修改话题")
    @PreAuthorize("hasAuthority('system:topic:update')")
    @PutMapping
    public Result updateTopic(@RequestBody TopicUpdateDTO topicUpdateDTO) {
        Topic topic = BeanUtils.copyBean(topicUpdateDTO, Topic.class).alter();
        boolean updateById = topicService.updateById(topic);
        return ResultUtils.update(updateById);
    }

    @ApiOperation("删除话题")
    @PreAuthorize("hasAuthority('system:topic:delete')")
    @ApiImplicitParam(name = "topicId", value = "话题id", required = true)
    @DeleteMapping("/{topicId}")
    public Result deleteTopic(@PathVariable(value = "topicId") Long topicId) {
        boolean deleteById = topicService.removeById(topicId);
        return ResultUtils.delete(deleteById);
    }

    @ApiOperation("批量删除话题")
    @PreAuthorize("hasAuthority('system:topic:delete')")
    @ApiImplicitParam(name = "topicIds", value = "话题id", required = true)
    @DeleteMapping("/batchDelete")
    public Result deleteTopics(@RequestParam("ids") List<Long> ids) {
        boolean delete = topicService.removeByIds(ids);
        return ResultUtils.delete(delete);
    }
}

