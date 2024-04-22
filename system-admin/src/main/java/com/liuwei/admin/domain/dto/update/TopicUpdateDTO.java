package com.liuwei.admin.domain.dto.update;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("话题修改实体")
public class TopicUpdateDTO {
    @ApiModelProperty(value = "主题id", required = true)
    private Long topicId;
    @ApiModelProperty(value = "类别id")
    private Long categoryId;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "用户id")
    private Long userId;
    @ApiModelProperty(value = "父id")
    private Long parentId;
    @ApiModelProperty(value = "是话题")
    private Integer isTopic;
    @ApiModelProperty(value = "点击数")
    private Integer clickCount;
    @ApiModelProperty(value = "权重")
    private float weight;
    @ApiModelProperty(value = "点击量")
    private Integer commentCount;
    @ApiModelProperty(value = "得分")
    private double score;
    @ApiModelProperty(value = "是否置顶")
    private Integer isTop;
    @ApiModelProperty(value = "点赞量")
    private Integer likeCount;
    @ApiModelProperty(value = "状态")
    private Integer status;
}
