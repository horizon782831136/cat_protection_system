package com.liuwei.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Image;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * (Image)表服务接口
 *
 * @author makejava
 * @since 2024-01-02 14:29:44
 */
@Service
public interface ImageService extends IService<Image>{
    public Result getImageByCondition(Integer current, Integer size, Long imageId, Long userId, Integer status);
    public Image getImageBasicInfo(Long imageId);
    public Result addImage(MultipartFile file);
    public boolean deleteImage(Long imageId);
    public boolean deleteImages(List<Long> ids);
}
