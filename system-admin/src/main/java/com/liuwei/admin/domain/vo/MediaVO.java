package com.liuwei.admin.domain.vo;

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
    @ApiModelProperty("用户ID")
    private Long userId;
    @ApiModelProperty("媒体类型")
    private String mediumType;
    @ApiModelProperty("对象名")
    private String name;
    @ApiModelProperty("原名")
    private String originalName;
    @ApiModelProperty("媒体路径")
    private String path;
    @ApiModelProperty("媒体详情")
    private String detail;
    @ApiModelProperty("媒体状态")
    private Integer status;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date updateTime;
}
