package com.liuwei.admin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuwei.admin.dao.TopicDao;
import com.liuwei.admin.domain.dto.add.TopicAddDTO;
import com.liuwei.admin.domain.vo.TopicVO;
import com.liuwei.admin.service.TopicService;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.dto.PageDTO;
import com.liuwei.framework.domain.po.Topic;
import com.liuwei.framework.enums.InitValue;
import com.liuwei.framework.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * (Topic)表服务实现类
 *
 * @author makejava
 * @since 2024-01-02 14:36:45
 */
@Service
@RequiredArgsConstructor
public class TopicServiceImpl extends ServiceImpl<TopicDao, Topic> implements TopicService {
    private final TopicDao topicDao;

    @Override
    public Result getTopicByCondition(Integer current, Integer size, Long topicId, Long userId, Long categoryId,
                                      String title, Integer isTopic, Long parentId, Integer isTop, Integer status) {
        LambdaQueryWrapper<Topic> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(ObjectUtil.isNotEmpty(topicId), Topic::getTopicId, topicId)
                .like(ObjectUtil.isNotEmpty(userId), Topic::getUserId, userId)
                .eq(ObjectUtil.isNotEmpty(categoryId), Topic::getCategoryId, categoryId)
                .like(ObjectUtil.isNotEmpty(title), Topic::getTitle, StringUtils.trimAllWhitespace(title))
                .eq(ObjectUtil.isNotEmpty(isTopic), Topic::getIsTopic, isTopic)
                .like(ObjectUtil.isNotEmpty(parentId), Topic::getParentId, parentId)
                .eq(ObjectUtil.isNotEmpty(isTop), Topic::getIsTop, isTop)
                .eq(ObjectUtil.isNotEmpty(status), Topic::getStatus, status)
                .orderByDesc(Topic::getCreateTime);
        Page<Topic> page = new Page<>(current, size);
        topicDao.selectPage(page, queryWrapper);
        PageDTO<TopicVO> of = PageDTO.of(page, TopicVO.class);
        return new Result(of);
    }

    @Override
    public boolean addTopic(TopicAddDTO topicAddDTO) {
        Topic topic = BeanUtils.copyBean(topicAddDTO, Topic.class).init()
                .setLikeCount(InitValue.INIT_LIKE_COUNT)
                .setClickCount(InitValue.INIT_CLICK_COUNT)
                .setCommentCount(InitValue.INIT_COMMENT_COUNT)
                .setScore(InitValue.INIT_SCORE)
                .setWeight(InitValue.INIT_WEIGHT);
        boolean save = topicDao.insert(topic) > 0;
        return save;
    }
}
