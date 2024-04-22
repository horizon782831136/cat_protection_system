package com.liuwei.user.utils;


import com.liuwei.framework.domain.po.Topic;
import com.liuwei.framework.enums.Default;

public class RecommendUtils {
    public static double getRecommendScore(Topic topic){
        double score = topic.getLikeCount() * Double.valueOf(Default.LIKE_WEIGHT.getKey()) +
                topic.getCommentCount() * Double.valueOf(Default.COMMENT_WEIGHT.getKey())
                + topic.getClickCount() * Double.valueOf(Default.CLICK_WEIGHT.getKey());
        return score;
    }


}
