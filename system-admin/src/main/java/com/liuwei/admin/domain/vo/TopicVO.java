package com.liuwei.admin.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(description = "话题实体")
public class TopicVO {
    @ApiModelProperty("话题ID")
    private Long topicId;
    @ApiModelProperty("分类ID")
    private Long categoryId;
    @ApiModelProperty("话题标题")
    private String title;
    @ApiModelProperty("话题内容")
    private String content;
    @ApiModelProperty("用户ID")
    private Long userId;
    @ApiModelProperty("父类ID")
    private Long parentId;
    @ApiModelProperty("是话题")
    private Integer isTopic;
    @ApiModelProperty("点击数")
    private Integer clickCount;
    @ApiModelProperty("话题权重")
    private float weight;
    @ApiModelProperty("评论数")
    private Integer commentCount;
    @ApiModelProperty("得分")
    private double score;
    @ApiModelProperty("是否置顶")
    private Integer isTop;
    @ApiModelProperty("点赞数")
    private Integer likeCount;
    @ApiModelProperty("话题状态")
    private Integer status;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date updateTime;
}
