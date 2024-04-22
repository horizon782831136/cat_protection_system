package com.liuwei.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Media;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * (Media)表服务接口
 *
 * @author makejava
 * @since 2024-02-10 20:02:55
 */
@Service
public interface MediaService extends IService<Media>{
    public Result getMediaByCondition(Integer current, Integer size, Long mediaId, Long topicId, Long userId,
                                      String mediumType, Integer status);
    public Result addMedia(MultipartFile file, Long topicId);

    public boolean deleteMedia(Long mediaId);

    public boolean deleteMedias(List<Long> ids);

}
