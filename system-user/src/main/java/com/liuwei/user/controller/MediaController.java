package com.liuwei.user.controller;


import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Media;
import com.liuwei.framework.utils.BeanUtils;
import com.liuwei.framework.utils.ResultUtils;
import com.liuwei.user.domain.dto.update.MediaUpdateDTO;
import com.liuwei.user.service.MediaService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

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
   //获取媒体文件基本信息
   @ApiOperation("获取媒体文件基本信息")
   @PreAuthorize("hasAuthority('front:media:list')")
   @ApiImplicitParam(name = "mediaId", value = "媒体id", required = true)
   @GetMapping("basic/{mediaId}")
   public Result getMediaBasicInfo(@PathVariable(value = "mediaId") Long mediaId){
      Media mediaBasicInfo = mediaService.getMediaBasicInfo(mediaId);
      return new Result(mediaBasicInfo);
   }

   //根据用户ID查询媒体文件信息
   @ApiOperation("根据用户ID查询媒体文件信息")
   @PreAuthorize("hasAuthority('front:media:list')")
   @ApiImplicitParams({
           @ApiImplicitParam(name = "userId", value = "用户ID", required = true),
           @ApiImplicitParam(name = "current", value = "当前页码", required = true),
           @ApiImplicitParam(name = "size", value = "每页条数", required = true)
   })
   @GetMapping("/userId")
   public Result getMediaByUserId(@RequestParam(value = "userId") Long userId,
                                  @RequestParam(value = "current", defaultValue = "1") Integer current,
                                  @RequestParam(value = "size", defaultValue = "10") Integer size
                                  ){
      return mediaService.getMediaByUserId(userId, current, size);
   }


   @ApiOperation("添加媒体文件")
   @PreAuthorize("hasAuthority('front:media:add')")
   @PostMapping
   public Result addMedia(@RequestParam("file") MultipartFile file, HttpServletRequest request){
      return mediaService.addMedia(file, request);
   }

   @ApiOperation("修改媒体文件")
   @PreAuthorize("hasAuthority('front:media:update')")
   @PutMapping
   public Result updateMedia(@RequestBody MediaUpdateDTO mediaUpdateDTO){
      Media media = BeanUtils.copyBean(mediaUpdateDTO, Media.class).alter();
      boolean update = mediaService.updateById(media);
      return ResultUtils.update(update);
   }

   @ApiOperation("删除媒体文件")
   @PreAuthorize("hasAuthority('front:media:delete')")
   @ApiImplicitParam(name = "mediaId", value = "媒体id", required = true)
   @DeleteMapping("/{mediaId}")
   public Result deleteMedia(@PathVariable(value = "mediaId") Long mediaId){
      boolean delete = mediaService.deleteMedia(mediaId);
      return ResultUtils.delete(delete);
   }


}

