package com.liuwei.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuwei.admin.domain.dto.add.TopicAddDTO;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Topic;
import org.springframework.stereotype.Service;

/**
 * (Topic)表服务接口
 *
 * @author makejava
 * @since 2024-01-02 14:36:45
 */
@Service
public interface TopicService extends IService<Topic> {

    Result getTopicByCondition(Integer current, Integer size, Long topicId, Long userId, Long categoryId,
            String title, Integer isTopic, Long parentId, Integer isTop, Integer status
    );

    boolean addTopic(TopicAddDTO topicAddDTO);


}
