package com.liuwei.user.domain.dto.update;

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
    @ApiModelProperty(value = "是否置顶")
    private Integer isTop;

}
