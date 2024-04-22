package com.liuwei.admin.domain.dto.add;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("话题添加实体")
public class TopicAddDTO {
    @ApiModelProperty(value = "类别id")
    private Long categoryId;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "内容", required = true)
    private String content;
    @ApiModelProperty(value = "用户id", required = true)
    private Long userId;
    @ApiModelProperty(value = "父id")
    private Long parentId;
    @ApiModelProperty(value = "是话题", required = true)
    private Integer isTopic;

    @ApiModelProperty(value = "是否置顶", required = true)
    private Integer isTop;
    @ApiModelProperty(value = "状态", required = true)
    private Integer status;
}
