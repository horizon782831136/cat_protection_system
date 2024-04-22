package com.liuwei.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.po.Topic;
import com.liuwei.user.domain.dto.add.CommentAddDTO;
import com.liuwei.user.domain.dto.add.TopicAddDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Topic)表服务接口
 *
 * @author makejava
 * @since 2024-01-02 14:36:45
 */
@Service
public interface TopicService extends IService<Topic>{


    Result addTopic(TopicAddDTO topic, Long userId);

    Result addComment(CommentAddDTO topic, Long userId);

    Result getTopicByCategoryId(Long categoryId, Integer current, Integer size);

    Result getTopicByUserId(Long userId, Integer current, Integer size);

    Result getHotTopic(Integer current, Integer size);

    Result getTopicByBatch(List<Long> topicIds);

    Result getTopicByCondition(Long categoryId, String title, Integer current, Integer size);

    Result getCommentByUserId(Long userId, Integer current, Integer size);

    Result getCommentByPid(Long topicId);

    Result getTopicByRecommend(Long userId, Integer current, Integer size);

    Result like(Long topicId);

    Result unlike(Long topicId);

    Result click(Long topicId);
}
