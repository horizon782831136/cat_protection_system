package com.liuwei.user.domain.dto.update;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("媒体修改实体")
public class MediaUpdateDTO {
    @ApiModelProperty(value = "媒体id", required = true)
    private Long mediaId;
    @ApiModelProperty(value = "话题id")
    private Long topicId;
    @ApiModelProperty(value = "用户id")
    private Long userId;
    @ApiModelProperty(value = "状态")
    private Integer status;
}
