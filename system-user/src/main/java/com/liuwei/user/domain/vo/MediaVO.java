package com.liuwei.user.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(description = "媒体实体")
public class MediaVO {
    @ApiModelProperty("媒体ID")
    private Long mediaId;
    @ApiModelProperty("主题ID")
    private Long topicId;
    @ApiModelProperty("媒体类型")
    private String mediumType;

    @ApiModelProperty("媒体路径")
    private String path;
    @ApiModelProperty("媒体详情")
    private String detail;
    @ApiModelProperty("媒体状态")
    private Integer status;
    @ApiModelProperty("创建时间")
    private Date createTime;
}
