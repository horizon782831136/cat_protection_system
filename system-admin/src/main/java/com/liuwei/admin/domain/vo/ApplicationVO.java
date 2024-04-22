package com.liuwei.admin.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(description = "申请实体")
public class ApplicationVO {
    @ApiModelProperty("申请ID")
    private Long applicationId;
    @ApiModelProperty("用户ID")
    private Long userId;
    @ApiModelProperty("申请类型")
    private Integer type;
    //将来存json类型的数据
    @ApiModelProperty("申请详情")
    private String detail;

    @ApiModelProperty("申请原因")
    private String reasonForApplication;

    @ApiModelProperty("推荐人ID")
    private Long referrer;

    @ApiModelProperty("审批理由")
    private String reasonForApproval;

    @ApiModelProperty("审批人ID")
    private Long administratorId;

    @ApiModelProperty("申请状态")
    private Integer status;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;
}
