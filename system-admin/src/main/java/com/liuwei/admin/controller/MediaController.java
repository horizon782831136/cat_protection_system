package com.liuwei.admin.controller;

import com.liuwei.admin.domain.dto.update.MediaUpdateDTO;
import com.liuwei.admin.service.MediaService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Media;
import com.liuwei.framework.utils.BeanUtils;
import com.liuwei.framework.utils.ResultUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * (Media)表控制层
 *
 * @author makejava
 * @since 2024-02-10 19:59:45
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("media")
public class MediaController {
   private final MediaService mediaService;


   @ApiOperation("媒体文件条件查询")
   @ApiImplicitParams({
           @ApiImplicitParam(name = "current", value = "当前页"),
           @ApiImplicitParam(name = "size", value = "每页条数"),
           @ApiImplicitParam(name = "mediaId", value = "媒体id"),
           @ApiImplicitParam(name = "topicId", value = "话题id"),
           @ApiImplicitParam(name = "userId", value = "用户id"),
           @ApiImplicitParam(name = "mediumType", value = "媒体类型"),
           @ApiImplicitParam(name = "status", value = "媒体状态")
   })
   @PreAuthorize("hasAuthority('system:media:list')")
   @GetMapping("/list")
   public Result getMediaByCondition(@RequestParam(value = "current", defaultValue = "1") Integer current,
                                     @RequestParam(value = "size", defaultValue = "10") Integer size,
                                     @RequestParam(value = "mediaId", required = false) Long mediaId,
                                     @RequestParam(value = "topicId", required = false) Long topicId,
                                     @RequestParam(value = "userId", required = false) Long userId,
                                     @RequestParam(value = "mediumType", required = false) String mediumType,
                                     @RequestParam(value = "status", required = false) Integer status
                                     ){
      return mediaService.getMediaByCondition(current, size, mediaId, topicId, userId, mediumType, status);
   }

   @ApiOperation("添加媒体文件")
   @ApiImplicitParams({
           @ApiImplicitParam(name = "file", value = "文件", required = true),
           @ApiImplicitParam(name = "topicId", value = "话题id", required = true)
   })
   @PreAuthorize("hasAuthority('system:media:add')")
   @PostMapping
   public Result addMedia(@RequestParam("file") MultipartFile file, @RequestParam("topicId") Long topicId){
      return mediaService.addMedia(file, topicId);
   }

   @ApiOperation("修改媒体文件")
   @PreAuthorize("hasAuthority('system:media:update')")
   @PutMapping
   public Result updateMedia(@RequestBody MediaUpdateDTO mediaUpdateDTO){
      Media media = BeanUtils.copyBean(mediaUpdateDTO, Media.class).alter();
      boolean update = mediaService.updateById(media);
      return ResultUtils.update(update);
   }

   @ApiOperation("删除媒体文件")
   @ApiImplicitParam(name = "mediaId", value = "媒体id", required = true)
   @DeleteMapping("/{mediaId}")
   @PreAuthorize("hasAuthority('system:media:delete')")
   public Result deleteMedia(@PathVariable(value = "mediaId") Long mediaId){
      boolean delete = mediaService.deleteMedia(mediaId);
      return ResultUtils.delete(delete);
   }

   @ApiOperation("批量删除")
   @PreAuthorize("hasAuthority('system:media:delete')")
   @ApiImplicitParam(name = "mediaIds", value = "媒体id", required = true)
   @DeleteMapping("/batchDelete")
   public Result deleteMedias(@RequestParam("ids") List<Long> ids){
      boolean delete = mediaService.deleteMedias(ids);
      return ResultUtils.delete(delete);
   }

}

