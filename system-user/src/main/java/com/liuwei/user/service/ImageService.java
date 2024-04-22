package com.liuwei.user.service;

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
    Result addImage(MultipartFile file);
    boolean deleteImage(Long imageId);
    boolean deleteImages(List<Long> ids);
    Image getImageBasicInfo(Long imageId);
    Result getImageByUserId(Long userId, Integer current, Integer size);
}
