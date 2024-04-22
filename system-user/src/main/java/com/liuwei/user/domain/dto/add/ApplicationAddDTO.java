package com.liuwei.user.domain.dto.add;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("应用申请实体")
@Data
public class ApplicationAddDTO {
    @ApiModelProperty(value = "用户id", required = true)
    private Long userId;

    @ApiModelProperty(value = "申请类型", required = true)
    private Integer type;

    @ApiModelProperty(value = "动物ID")
    private Long animalId;

    @ApiModelProperty(value = "申请理由", required = true)
    private String reasonForApplication;

    @ApiModelProperty(value = "推荐人id")
    private Long referrer;

    @ApiModelProperty(value = "申请状态", required = true)
    private Integer status;
}
