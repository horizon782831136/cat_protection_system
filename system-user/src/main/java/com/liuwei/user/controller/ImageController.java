package com.liuwei.user.controller;

import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Image;
import com.liuwei.framework.utils.BeanUtils;
import com.liuwei.framework.utils.ResultUtils;
import com.liuwei.user.domain.dto.update.ImageUpdateDTO;
import com.liuwei.user.service.ImageService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * (Image)表控制层
 *
 * @author makejava
 * @since 2024-02-10 19:58:54
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("image")

public class ImageController {
   private final ImageService imageService;
   //查询图片基本信息
   @ApiOperation("查询图片基本信息")
   @PreAuthorize("hasAuthority('front:image:list')")
   @ApiImplicitParam(name = "imageId", value = "图片id", required = true)
   @GetMapping("/basic/{imageId}")
   public Result getImageBasicInfo(@PathVariable(value = "imageId") Long imageId)
   {
      Image image = imageService.getImageBasicInfo(imageId);
      return new Result(image);
   }

   //根据用户ID查询图片信息
   @PreAuthorize("hasAuthority('front:image:list')")
   @ApiOperation("根据用户ID查询图片信息")
   @ApiImplicitParams({
           @ApiImplicitParam(name = "userId", value = "用户ID", required = true),
           @ApiImplicitParam(name = "current", value = "当前页码", required = true),
           @ApiImplicitParam(name = "size", value = "每页条数", required = true)
   })
   @GetMapping("/userId")
   public Result getImageByUserId(@RequestParam(value = "userId") Long userId,
                                  @RequestParam(value = "current", defaultValue = "1") Integer current,
                                  @RequestParam(value = "size", defaultValue = "10") Integer size
                                  ){
      return imageService.getImageByUserId(userId, current, size);
   }

   @ApiOperation("添加图片")
   @PreAuthorize("hasAuthority('front:image:add')")
   @ApiImplicitParam(name = "file", value = "文件", required = true)
   @PostMapping
   public Result addImage(@RequestParam("file") MultipartFile file)
   {
      return imageService.addImage(file);
   }

   @ApiOperation("修改图片")
   @PutMapping
   @PreAuthorize("hasAuthority('front:image:update')")
   public Result updateImage(@RequestBody ImageUpdateDTO imageUpdateDTO)
   {
      Image image = BeanUtils.copyBean(imageUpdateDTO, Image.class).alter();
      boolean update = imageService.updateById(image);
      return ResultUtils.update(update);
   }

   @ApiOperation("删除图片")
   @PreAuthorize("hasAuthority('front:image:delete')")
   @ApiImplicitParam(name = "imageId", value = "图片id", required = true)
   @DeleteMapping("/{imageId}")
   public Result deleteImage(@PathVariable(value = "imageId") Long imageId)
   {
      boolean delete = imageService.deleteImage(imageId);
      return ResultUtils.delete(delete);
   }
}

