package com.liuwei.user.domain.dto.update;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("申请修改实体")
public class ApplicationUpdateDTO {
    @ApiModelProperty(value = "申请id", required = true)
    private Long applicationId;

    @ApiModelProperty(value = "申请理由")
    private String reasonForApplication;

    @ApiModelProperty(value = "推荐人id")
    private Long referrer;

    @ApiModelProperty(value = "申请状态")
    private Integer status;
}
