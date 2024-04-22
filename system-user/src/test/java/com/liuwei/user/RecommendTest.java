package com.liuwei.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liuwei.framework.domain.po.Recommended;
import com.liuwei.framework.domain.po.Topic;
import com.liuwei.user.domain.bo.RecommendedBO;
import com.liuwei.user.service.RecommendedService;
import com.liuwei.user.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@RequiredArgsConstructor
public class RecommendTest {
    @Autowired
    private RecommendedService recommendedService;

    @Autowired
    private TopicService topicService;
    @Test
    public void testAddRecommend() {
        Recommended recommended = new Recommended();
        recommended.setCategoryId(1753709564299079682L);
        recommended.setUserId(1752757742369554433L);
        recommended.setDelFlag(0);
        recommended.setStatus(1);
        recommended.setScore(1.0f);
        recommended.setCoefficient(1.0f);
        recommended.setCreateTime(new Date());
        recommendedService.save(recommended);
    }

    @Test
    public void testRecommended() {
        //根据推荐系数得分，以及话题：权重、点击数、评论数、点赞数
        int total = 50;
        float tSum = 0;
        for(Recommended recommended : recommendedService.list()){
            tSum += recommended.getScore() * recommended.getCoefficient();
        }
        final float sum = tSum;
        List<RecommendedBO> collect = recommendedService.list().stream()
                .map(recommended -> {
                    long id = recommended.getCategoryId();
                    int size = Math.round(recommended.getScore() * recommended.getCoefficient() / sum * total);
                    System.out.println("score : " + recommended.getScore() + " coe : " + recommended.getCoefficient());
                    return new RecommendedBO(id, size);
                }).collect(Collectors.toList());

        List<Topic> topics = new ArrayList<>();
        for(RecommendedBO recommendedBO : collect){
            LambdaQueryWrapper<Topic> queryWrapper = new LambdaQueryWrapper<Topic>();
            queryWrapper.eq(Topic::getCategoryId, recommendedBO.getCategoryId())
                    .orderByDesc(Topic::getScore)
                    .last("limit " + recommendedBO.getSize());
            topicService.list(queryWrapper).forEach(topic -> topics.add(topic));
        }

        //离散化只需要根据主键ID排序

        for(int i = 0; i < topics.size(); i++)
            System.out.println(i + " " + topics.get(i));


    }
}
