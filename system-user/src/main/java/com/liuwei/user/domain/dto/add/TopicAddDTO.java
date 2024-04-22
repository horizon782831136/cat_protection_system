package com.liuwei.user.domain.dto.add;

import com.liuwei.framework.domain.bo.Content;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("话题添加实体")
public class TopicAddDTO {
    @ApiModelProperty(value = "类别id", required = true)
    private Long categoryId;
    @ApiModelProperty(value = "标题", required = true)
    private String title;
    @ApiModelProperty(value = "内容", required = true)
    private List<Content> contents;

    @ApiModelProperty(value = "上传列表", required = true)
    private List<Long> uploadList;

    @ApiModelProperty(value = "是否置顶", required = true)
    private Integer isTop;

    @ApiModelProperty(value = "状态", required = true)
    private Integer status;

}
