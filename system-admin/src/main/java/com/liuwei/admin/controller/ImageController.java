package com.liuwei.admin.controller;

import com.liuwei.admin.domain.dto.update.ImageUpdateDTO;
import com.liuwei.admin.service.ImageService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Image;
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

   @ApiOperation("图片条件查询")
   @ApiImplicitParams({
           @ApiImplicitParam(name = "current", value = "当前页"),
           @ApiImplicitParam(name = "size", value = "每页条数"),
           @ApiImplicitParam(name = "imageId", value = "图片id"),
           @ApiImplicitParam(name = "userId", value = "用户id"),
           @ApiImplicitParam(name = "status", value = "图片状态")
   })
   @GetMapping("/list")
   @PreAuthorize("hasAuthority('system:image:list')")
   public Result getImageByCondition(@RequestParam(value = "current", defaultValue = "1") Integer current,
                                   @RequestParam(value = "size", defaultValue = "10") Integer size,
                                   @RequestParam(value = "imageId", required = false) Long imageId,
                                   @RequestParam(value = "userId", required = false) Long userId,
                                   @RequestParam(value = "status", required = false) Integer status
                                   ){
      return imageService.getImageByCondition(current, size, imageId, userId, status);
   }

   @ApiOperation("添加图片")
   @PreAuthorize("hasAuthority('system:image:add')")
   @ApiImplicitParam(name = "file", value = "文件", required = true)
   @PostMapping
   public Result addImage(@RequestParam("file") MultipartFile file)
   {
      return imageService.addImage(file);
   }

   @ApiOperation("修改图片")
   @PutMapping
   @PreAuthorize("hasAuthority('system:image:update')")
   public Result updateImage(@RequestBody ImageUpdateDTO imageUpdateDTO)
   {
      Image image = BeanUtils.copyBean(imageUpdateDTO, Image.class).alter();
      boolean update = imageService.updateById(image);
      return ResultUtils.update(update);
   }

   @ApiOperation("删除图片")
   @PreAuthorize("hasAuthority('system:image:delete')")
   @ApiImplicitParam(name = "imageId", value = "图片id", required = true)
   @DeleteMapping("/{imageId}")
   public Result deleteImage(@PathVariable(value = "imageId") Long imageId)
   {
      boolean delete = imageService.deleteImage(imageId);
      return ResultUtils.delete(delete);
   }


   @ApiOperation("批量删除")
   @PreAuthorize("hasAuthority('system:image:delete')")
   @ApiImplicitParam(name = "imageIds", value = "图片id", required = true)
   @DeleteMapping("/batchDelete")
   public Result deleteImages(@RequestParam("ids") List<Long> ids)
   {
      boolean delete = imageService.deleteImages(ids);
      return ResultUtils.delete(delete);
   }
}

