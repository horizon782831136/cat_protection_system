package com.liuwei.user.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuwei.framework.domain.Result;
import com.liuwei.framework.domain.bo.Content;
import com.liuwei.framework.domain.dto.PageDTO;
import com.liuwei.framework.domain.po.*;
import com.liuwei.framework.enums.*;
import com.liuwei.framework.utils.BeanUtils;
import com.liuwei.framework.utils.KeyWordFilterTrie;
import com.liuwei.framework.utils.RedisCache;
import com.liuwei.framework.utils.ResultUtils;
import com.liuwei.user.dao.TopicDao;
import com.liuwei.user.domain.bo.RecommendedBO;
import com.liuwei.user.domain.dto.LoginUser;
import com.liuwei.user.domain.dto.add.CommentAddDTO;
import com.liuwei.user.domain.dto.add.TopicAddDTO;
import com.liuwei.user.domain.vo.CommentVO;
import com.liuwei.user.domain.vo.TopicVO;
import com.liuwei.user.domain.vo.UserBasicVO;
import com.liuwei.user.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;


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
    private final RecommendedService recommendedService;
    private final ImageService imageService;
    private final UserService userService;
    private final FollowersService followersService;
    private final MediaService mediaService;
    private final RedisCache redisCache;
    @Override
    public Result addTopic(TopicAddDTO topicAddDTO, Long userId) {


        Topic topic = BeanUtils.copyBean(topicAddDTO, Topic.class);
        topic.setIsTopic(Type.IS_TOPIC.getKey())
                .setUserId(userId)
                .setLikeCount(InitValue.INIT_LIKE_COUNT)
                .setClickCount(InitValue.INIT_CLICK_COUNT)
                .setWeight(InitValue.INIT_WEIGHT)
                .setCommentCount(InitValue.INIT_COMMENT_COUNT)
                .init();
        topicAddDTO.getContents().forEach(content -> {
            if(content.getType().equals("text")){
                //进行过滤
                content.setContent(KeyWordFilterTrie.filterKeywords(content.getContent()));
            }
        });
        topic.setTitle(KeyWordFilterTrie.filterKeywords(topic.getTitle()));

        topic.setContent(JSON.toJSONString(topicAddDTO.getContents()));
        //删除未使用的媒体文件
        List<Long> uploadList = topicAddDTO.getUploadList();
        Set<Long> set = new HashSet<>(uploadList);
        topicAddDTO.getContents().forEach(content -> {
            if(content.getType().equals("image") || content.getType().equals("video") || content.getType().
                    equals("audio")){
                Long mediaId = Long.valueOf(content.getContent());
                if(!set.contains(mediaId)){
                    mediaService.deleteMedia(mediaId);
                }
            }
        });
        boolean flag = topicDao.insert(topic) > 0;
        return ResultUtils.add(flag);
    }

    @Override
    @Transactional
    public Result addComment(CommentAddDTO commentAddDTO, Long userId) {

        Topic topic = BeanUtils.copyBean(commentAddDTO, Topic.class);
        topic.setIsTopic(Type.NOT_TOPIC.getKey())
                .setIsTop(Status.NOT_TOP.getKey())
                .setUserId(userId)
                .setLikeCount(InitValue.INIT_LIKE_COUNT)
                .setClickCount(InitValue.INIT_CLICK_COUNT)
                .setWeight(InitValue.INIT_WEIGHT)
                .setCommentCount(InitValue.INIT_COMMENT_COUNT)
                .setStatus(Status.NORMAL_STATUS.getKey())
                .init();
        commentAddDTO.getContents().forEach(content -> {
            if(content.getType().equals("text")){
                //进行过滤
                content.setContent(KeyWordFilterTrie.filterKeywords(content.getContent()));
            }
        });
        topic.setContent(JSON.toJSONString(commentAddDTO.getContents()));

        //添加评论数
        LambdaQueryWrapper<Topic> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Topic::getTopicId, topic.getParentId())
                .select(Topic::getCommentCount, Topic::getScore);
        Topic pTopic = topicDao.selectOne(lambdaQueryWrapper);
        pTopic.setTopicId(topic.getParentId())
                .setCommentCount(pTopic.getCommentCount() + 1)
                .setScore(pTopic.getScore() + Double.valueOf(Default.COMMENT_WEIGHT.getKey()));
        boolean flag = topicDao.insert(topic) > 0;
        topicDao.updateById(pTopic);
        //删除未使用的媒体文件
        List<Long> uploadList = commentAddDTO.getUploadList();
        Set<Long> set = new HashSet<>(uploadList);
        commentAddDTO.getContents().forEach(content -> {
            if(content.getType().equals("image") || content.getType().equals("video") || content.getType().equals("audio")){
                Long mediaId = Long.valueOf(content.getContent());
                if(!set.contains(mediaId)){
                    mediaService.deleteMedia(mediaId);
                }
            }
        });

        return ResultUtils.add(flag);
    }

    @Override
    public Result getTopicByCategoryId(Long categoryId, Integer current, Integer size) {
        LambdaQueryWrapper<Topic> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Topic::getCategoryId, categoryId)
                .eq(Topic::getStatus, Status.PUBLICLY_VISIBLE.getKey())
                .eq(Topic::getIsTopic, Type.IS_TOPIC.getKey())
                .orderByDesc(Topic::getScore);
        Page<Topic> page = new Page<>(current, size);
        topicDao.selectPage(page, queryWrapper);
        List<TopicVO> collect = page.getRecords().stream().map(topic -> {
            LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(User::getUserId, topic.getUserId())
                    .select(User::getNickname, User::getAvatar, User::getUserId, User::getRole, User::getStatus, User::getOccupation);
            User user = userService.getOne(lambdaQueryWrapper);
            Image imageBasicInfo = imageService.getImageBasicInfo(user.getAvatar());
            UserBasicVO userBasicVO = BeanUtils.copyBean(user, UserBasicVO.class);
            userBasicVO.setAvatar(imageBasicInfo.getPath());
            TopicVO topicVO = BeanUtils.copyBean(topic, TopicVO.class);
            topicVO.setUser(userBasicVO);
            userBasicVO.setFollowerCount(followersService.getFollowerCount(topic.getUserId()));
            userBasicVO.setFolloweeCount(followersService.getFolloweeCount(topic.getUserId()));

            TypeReference<List<Content>> typeRef = new TypeReference<List<Content>>() {};
            List<Content> contents = JSON.parseObject(topic.getContent(), typeRef);
            contents.forEach(content -> {
                if (content.getType().equals(Default.VIDEO.getKey()) || content.getType().equals(Default.IMAGE.getKey())
                        || content.getType().equals(Default.AUDIO.getKey())
                ) {
                    Long mediaId = Long.valueOf(content.getContent());
                    Media mediaBasicInfo = mediaService.getMediaBasicInfo(mediaId);
                    content.setContent(mediaBasicInfo.getPath());
                }
            });
            topicVO.setContents(contents);
            return topicVO;
        }).collect(Collectors.toList());
        Page<TopicVO> newPage = new Page<>(current, size);
        newPage.setTotal(page.getTotal()).setRecords(collect);
        PageDTO<TopicVO> of = PageDTO.of(newPage, TopicVO.class);
        return new Result(of);
    }

    @Override
    public Result getTopicByUserId(Long userId, Integer current, Integer size) {
        LambdaQueryWrapper<Topic> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Topic::getUserId, userId)
                .eq(Topic::getIsTopic, Type.IS_TOPIC.getKey())
                .orderByDesc(Topic::getIsTop)
                .orderByDesc(Topic::getCreateTime);
        Page<Topic> page = new Page<>(current, size);
        topicDao.selectPage(page, queryWrapper);
        List<TopicVO> collect = page.getRecords().stream().map(topic -> {
            TopicVO topicVO = BeanUtils.copyBean(topic, TopicVO.class);
            TypeReference<List<Content>> typeRef = new TypeReference<List<Content>>() {};
            List<Content> contents = JSON.parseObject(topic.getContent(), typeRef);
            contents.forEach(content -> {
                if (content.getType().equals(Default.VIDEO.getKey()) || content.getType().equals(Default.IMAGE.getKey())
                        || content.getType().equals(Default.AUDIO.getKey())
                ) {
                    Long mediaId = Long.valueOf(content.getContent());
                    Media mediaBasicInfo = mediaService.getMediaBasicInfo(mediaId);
                    content.setContent(mediaBasicInfo.getPath());
                }
            });
            topicVO.setContents(contents);
            return topicVO;
        }).collect(Collectors.toList());
        Page<TopicVO> newPage = new Page<>(current, size);
        newPage.setRecords(collect).setTotal(page.getTotal());
        PageDTO<TopicVO> of = PageDTO.of(newPage, TopicVO.class);
        return new Result(of);
    }

    @Override
    public Result getHotTopic(Integer current, Integer size) {
        LambdaQueryWrapper<Topic> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Topic::getIsTopic, Type.IS_TOPIC.getKey())
                .eq(Topic::getStatus, Status.PUBLICLY_VISIBLE.getKey())
                .orderByDesc(Topic::getScore);
        Page<Topic> page = new Page<>(current, size);
        topicDao.selectPage(page, queryWrapper);
        List<TopicVO> collect = page.getRecords().stream().map(topic -> {
            LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(User::getUserId, topic.getUserId())
                    .select(User::getNickname, User::getAvatar, User::getUserId, User::getRole, User::getStatus, User::getOccupation,
                            User::getSignature
                    );
            User user = userService.getOne(lambdaQueryWrapper);
            Image imageBasicInfo = imageService.getImageBasicInfo(user.getAvatar());
            UserBasicVO userBasicVO = BeanUtils.copyBean(user, UserBasicVO.class);
            userBasicVO.setAvatar(imageBasicInfo.getPath());
            TopicVO topicVO = BeanUtils.copyBean(topic, TopicVO.class);
            topicVO.setUser(userBasicVO);
            userBasicVO.setFollowerCount(followersService.getFollowerCount(topic.getUserId()));
            userBasicVO.setFolloweeCount(followersService.getFolloweeCount(topic.getUserId()));

            TypeReference<List<Content>> typeRef = new TypeReference<List<Content>>() {};
            List<Content> contents = JSON.parseObject(topic.getContent(), typeRef);
            contents.forEach(content -> {
                if (content.getType().equals(Default.VIDEO.getKey()) || content.getType().equals(Default.IMAGE.getKey())
                        || content.getType().equals(Default.AUDIO.getKey())
                ) {
                    Long mediaId = Long.valueOf(content.getContent());
                    Media mediaBasicInfo = mediaService.getMediaBasicInfo(mediaId);
                    content.setContent(mediaBasicInfo.getPath());
                }
            });
            topicVO.setContents(contents);
            return topicVO;
        }).collect(Collectors.toList());
        Collections.sort(collect, Comparator.comparing(TopicVO::getTopicId));
        Page<TopicVO> newPage = new Page<>(current, size);
        newPage.setTotal(page.getTotal()).setRecords(collect);
        PageDTO<TopicVO> of = PageDTO.of(newPage, TopicVO.class);
        return new Result(of);
    }

    @Override
    public Result getTopicByBatch(List<Long> topicIds) {
        LambdaQueryWrapper<Topic> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Topic::getTopicId, topicIds)
                .eq(Topic::getStatus, Status.PUBLICLY_VISIBLE.getKey())
                .eq(Topic::getIsTopic, Type.IS_TOPIC.getKey())
                .orderByDesc(Topic::getScore);
        List<Topic> topics = topicDao.selectList(queryWrapper);
        List<TopicVO> collect = topics.stream().map(topic -> {
            TopicVO topicVO = BeanUtils.copyBean(topic, TopicVO.class);
            TypeReference<List<Content>> typeRef = new TypeReference<List<Content>>() {};
            List<Content> contents = JSON.parseObject(topic.getContent(), typeRef);
            contents.forEach(content -> {
                if (content.getType().equals(Default.VIDEO.getKey()) || content.getType().equals(Default.IMAGE.getKey())
                        || content.getType().equals(Default.AUDIO.getKey())
                ) {
                    Long mediaId = Long.valueOf(content.getContent());
                    Media mediaBasicInfo = mediaService.getMediaBasicInfo(mediaId);
                    content.setContent(mediaBasicInfo.getPath());
                }
            });
            topicVO.setContents(contents);
            return topicVO;
        }).collect(Collectors.toList());

        return new Result(collect);
    }

    @Override
    public Result getTopicByCondition(Long categoryId, String title, Integer current, Integer size) {
        //todo 后续可以结合es
        LambdaQueryWrapper<Topic> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(ObjectUtil.isNotEmpty(title), Topic::getTitle, title)
                .eq(ObjectUtil.isNotEmpty(categoryId), Topic::getCategoryId, categoryId)
                .eq(Topic::getStatus, Status.PUBLICLY_VISIBLE.getKey())
                .eq(Topic::getIsTopic, Type.IS_TOPIC.getKey())
                .orderByDesc(Topic::getScore);
        Page<Topic> page = new Page<>(current, size);
        topicDao.selectPage(page, queryWrapper);
        List<TopicVO> collect = page.getRecords().stream().map(topic -> {
            TopicVO topicVO = BeanUtils.copyBean(topic, TopicVO.class);
            TypeReference<List<Content>> typeRef = new TypeReference<>() {};
            List<Content> contents = JSON.parseObject(topic.getContent(), typeRef);
            contents.forEach(content -> {
                if (content.getType().equals(Default.VIDEO.getKey()) || content.getType().equals(Default.IMAGE.getKey())
                        || content.getType().equals(Default.AUDIO.getKey())
                ) {
                    Long mediaId = Long.valueOf(content.getContent());
                    Media mediaBasicInfo = mediaService.getMediaBasicInfo(mediaId);
                    content.setContent(mediaBasicInfo.getPath());
                }
            });
            topicVO.setContents(contents);
            return topicVO;
        }).collect(Collectors.toList());
        Collections.sort(collect, Comparator.comparing(TopicVO::getTopicId));
        Page<TopicVO> newPage = new Page<>(current, size);
        newPage.setRecords(collect).setTotal(page.getTotal());
        PageDTO<TopicVO> of = PageDTO.of(newPage, TopicVO.class);
        return new Result(of);
    }

    @Override
    public Result getCommentByUserId(Long userId, Integer current, Integer size) {
        LambdaQueryWrapper<Topic> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Topic::getUserId, userId)
                .eq(Topic::getIsTopic, Type.NOT_TOPIC.getKey())
                .orderByDesc(Topic::getCreateTime);
        Page<Topic> page = new Page<>(current, size);
        topicDao.selectPage(page, queryWrapper);
        List<CommentVO> collect = page.getRecords().stream().map(topic -> {
            CommentVO commentVO = BeanUtils.copyBean(topic, CommentVO.class);
            TypeReference<List<Content>> typeRef = new TypeReference<>() {};
            List<Content> contents = JSON.parseObject(topic.getContent(), typeRef);
            contents.forEach(content -> {
                if (content.getType().equals(Default.VIDEO.getKey()) || content.getType().equals(Default.IMAGE.getKey())
                        || content.getType().equals(Default.AUDIO.getKey())
                ) {
                    Long mediaId = Long.valueOf(content.getContent());
                    Media mediaBasicInfo = mediaService.getMediaBasicInfo(mediaId);
                    content.setContent(mediaBasicInfo.getPath());
                }
            });
            commentVO.setContents(contents);
            return commentVO;
        }).collect(Collectors.toList());
        Page<CommentVO> newPage = new Page<>(current, size);
        newPage.setRecords(collect).setTotal(page.getTotal());
        PageDTO<TopicVO> of = PageDTO.of(newPage, TopicVO.class);
        return new Result(of);
    }

    @Override
    public Result getCommentByPid(Long pid) {
        LambdaQueryWrapper<Topic> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Topic::getParentId, pid)
                .eq(Topic::getIsTopic, Type.NOT_TOPIC.getKey())
                .orderByDesc(Topic::getCreateTime)
                .ge(Topic::getStatus, Status.NORMAL_STATUS)
                .orderByDesc(Topic::getScore);
        List<Topic> topics = topicDao.selectList(queryWrapper);
        List<CommentVO> collect = topics.stream().map(topic -> {
            CommentVO commentVO = BeanUtils.copyBean(topic, CommentVO.class);
            Long userId = topic.getUserId();
            LambdaQueryWrapper<User> userQueryWrapper = new LambdaQueryWrapper<>();
            userQueryWrapper.eq(User::getUserId, userId)
                    .select(User::getNickname, User::getAvatar);
            User user = userService.getOne(userQueryWrapper);
            Image image = imageService.getImageBasicInfo(user.getAvatar());
            commentVO.setAvatar(image.getPath());
            commentVO.setNickname(user.getNickname());
            TypeReference<List<Content>> typeRef = new TypeReference<>() {};
            List<Content> contents = JSON.parseObject(topic.getContent(), typeRef);
            contents.forEach(content -> {
                if (content.getType().equals(Default.VIDEO.getKey()) || content.getType().equals(Default.IMAGE.getKey())
                        || content.getType().equals(Default.AUDIO.getKey())
                ) {
                    Long mediaId = Long.valueOf(content.getContent());
                    Media mediaBasicInfo = mediaService.getMediaBasicInfo(mediaId);
                    content.setContent(mediaBasicInfo.getPath());
                }
            });
            commentVO.setContents(contents);
            return commentVO;
        }).collect(Collectors.toList());
        return new Result(collect);
    }

    @Override
    public Result getTopicByRecommend(Long userId, Integer current, Integer size) {
        //todo 后期要加入分页
        //首先看用户是否存在，若存在就按照用户推荐，不存在就按，系统推荐
        final int TOPIC_SIZE = Integer.parseInt(Default.RECOMMEND_SIZE.getKey());
        float tSum = 0;
        List<Recommended> recommendeds;
        LambdaQueryWrapper<Recommended> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotEmpty(userId)) {
            queryWrapper.eq(Recommended::getUserId, userId)
                    .orderByDesc(Recommended::getScore)
                    .eq(Recommended::getStatus, Status.NORMAL_STATUS.getKey());
            recommendeds = recommendedService.list(queryWrapper);
        } else {
            queryWrapper.eq(Recommended::getUserId, Cate.SYSTEM.getKey())
                    .orderByDesc(Recommended::getScore)
                    .eq(Recommended::getStatus, Status.NORMAL_STATUS.getKey());
            recommendeds = recommendedService.list(queryWrapper);
        }
        for (Recommended recommended : recommendeds) {
            tSum += recommended.getScore() * recommended.getCoefficient();
        }
        final float SUM = tSum;
        List<RecommendedBO> collect = recommendeds.stream().map(
                recommended -> {
                    long id = recommended.getCategoryId();
                    int len = Math.round(recommended.getScore() * recommended.getCoefficient() / SUM * TOPIC_SIZE);
                    return new RecommendedBO(id, len);
                }
        ).collect(Collectors.toList());
        List<Topic> topics = new ArrayList<>();
        for (RecommendedBO recommendedBO : collect) {
            LambdaQueryWrapper<Topic> topicLambdaQueryWrapper = new LambdaQueryWrapper<>();
            topicLambdaQueryWrapper.eq(Topic::getCategoryId, recommendedBO.getCategoryId())
                    .eq(Topic::getStatus, Status.PUBLICLY_VISIBLE.getKey())
                    .eq(Topic::getIsTopic, Type.IS_TOPIC.getKey())
                    .orderByDesc(Topic::getScore)
                    .last("limit " + recommendedBO.getSize());
            topicDao.selectList(topicLambdaQueryWrapper).forEach(topic -> topics.add(topic));
        }
        Collections.sort(topics, Comparator.comparing(Topic::getTopicId));
        List<TopicVO> topicVOS = topics.stream().map(topic -> {
            TopicVO topicVO = BeanUtils.copyBean(topic, TopicVO.class);
            LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userLambdaQueryWrapper.eq(User::getUserId, topic.getUserId())
                    .select(User::getNickname, User::getAvatar, User::getUserId, User::getRole, User::getStatus,
                            User::getOccupation,
                            User::getSignature

                    );
            User one = userService.getOne(userLambdaQueryWrapper);
            UserBasicVO userBasicVO = BeanUtils.copyBean(one, UserBasicVO.class);
            Image imageBasicInfo = imageService.getImageBasicInfo(one.getAvatar());
            userBasicVO.setAvatar(imageBasicInfo.getPath());
            userBasicVO.setFollowerCount(followersService.getFollowerCount(topic.getUserId()));
            userBasicVO.setFolloweeCount(followersService.getFolloweeCount(topic.getUserId()));
            topicVO.setUser(userBasicVO);
            TypeReference<List<Content>> typeRef = new TypeReference<>() {};
            List<Content> contents = JSON.parseObject(topic.getContent(), typeRef);
            contents.forEach(content -> {
                if (content.getType().equals(Default.VIDEO.getKey()) || content.getType().equals(Default.IMAGE.getKey())
                        || content.getType().equals(Default.AUDIO.getKey())
                ) {
                    Long mediaId = Long.valueOf(content.getContent());
                    Media mediaBasicInfo = mediaService.getMediaBasicInfo(mediaId);
                    content.setContent(mediaBasicInfo.getPath());
                }
            });
            topicVO.setContents(contents);
            return topicVO;
        }).collect(Collectors.toList());

        return new Result(topicVOS);
    }

    @Override
    @Transactional
    public Result like(Long topicId) {
        //todo 点赞后续要关联用户
        LambdaQueryWrapper<Topic> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Topic::getTopicId, topicId)
                .select(Topic::getLikeCount, Topic::getScore);
        Topic topic = topicDao.selectOne(queryWrapper).setTopicId(topicId);
        topic.setLikeCount(topic.getLikeCount() + 1);
        topic.setScore(topic.getScore() + Double.valueOf(Default.LIKE_WEIGHT.getKey()));
        topicDao.updateById(topic);
       return null;
    }

    @Override
    @Transactional
    public Result unlike(Long topicId) {
        //todo 点赞后续要关联用户
        LambdaQueryWrapper<Topic> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Topic::getTopicId, topicId)
                .select(Topic::getLikeCount, Topic::getScore);
        Topic topic = topicDao.selectOne(queryWrapper).setTopicId(topicId);
        topic.setLikeCount(topic.getLikeCount() - 1);
        topic.setScore(topic.getScore() - Double.valueOf(Default.LIKE_WEIGHT.getKey()));
        topicDao.updateById(topic);
        return null;
    }

    @Override
    @Transactional
    public Result click(Long topicId) {
        LambdaQueryWrapper<Topic> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Topic::getTopicId, topicId)
                .select(Topic::getClickCount, Topic::getScore);
        Topic topic = topicDao.selectOne(queryWrapper).setTopicId(topicId);
        topic.setClickCount(topic.getClickCount() + 1);
        topic.setScore(topic.getScore() + Double.valueOf(Default.CLICK_WEIGHT.getKey()));
        topicDao.updateById(topic);
      return null;
    }
}
