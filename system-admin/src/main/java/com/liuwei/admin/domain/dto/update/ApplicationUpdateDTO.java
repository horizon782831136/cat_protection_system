package com.liuwei.admin.domain.dto.update;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("申请修改实体")
public class ApplicationUpdateDTO {
    @ApiModelProperty(value = "申请id", required = true)
    private Long applicationId;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "申请类型")
    private Integer type;

    //将来存json类型的数据
    @ApiModelProperty(value = "申请详情")
    private String detail;

    @ApiModelProperty(value = "申请理由")
    private String reasonForApplication;

    @ApiModelProperty(value = "推荐人id")
    private Long referrer;

    @ApiModelProperty(value = "审批理由")
    private String reasonForApproval;

    @ApiModelProperty(value = "申请状态")
    private Integer status;
}
