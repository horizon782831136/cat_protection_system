package com.liuwei.user.domain.vo;

import com.liuwei.framework.domain.bo.Content;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel(description = "话题实体")
public class TopicVO {
    @ApiModelProperty("话题ID")
    private Long topicId;
    @ApiModelProperty("分类ID")
    private Long categoryId;
    @ApiModelProperty("话题标题")
    private String title;
    @ApiModelProperty("话题数据")
    List<Content> contents;

    @ApiModelProperty("用户")
    private UserBasicVO user;

    @ApiModelProperty("点击数")
    private Integer clickCount;
    @ApiModelProperty("评论数")
    private Integer commentCount;

    @ApiModelProperty("是否置顶")
    private Integer isTop;
    @ApiModelProperty("点赞数")
    private Integer likeCount;
    @ApiModelProperty("话题状态")
    private Integer status;
    @ApiModelProperty("创建时间")
    private Date createTime;
}
