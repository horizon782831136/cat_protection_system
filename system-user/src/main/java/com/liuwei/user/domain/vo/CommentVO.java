package com.liuwei.user.domain.vo;

import com.liuwei.framework.domain.bo.Content;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel(description = "评论实体")
public class CommentVO {
    @ApiModelProperty("话题ID")
    private Long topicId;
    @ApiModelProperty("话题内容")
    private List<Content> contents;
    @ApiModelProperty("用户ID")
    private Long userId;
    @ApiModelProperty("名称")
    private String nickname;
    @ApiModelProperty("头像")
    private String avatar;
    @ApiModelProperty("父类ID")
    private Long parentId;
    @ApiModelProperty("点击数")
    private Integer clickCount;
    @ApiModelProperty("评论数")
    private Integer commentCount;
    @ApiModelProperty("是否置顶")
    private Integer isTop;
    @ApiModelProperty("点赞数")
    private Integer likeCount;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("话题状态")
    private Integer status;
}
