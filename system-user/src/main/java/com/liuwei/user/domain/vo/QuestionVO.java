package com.liuwei.user.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(description = "问题实体")
public class QuestionVO {
    @ApiModelProperty("问题ID")
    private Long questionId;
    @ApiModelProperty("问题描述")
    private String description;
    @ApiModelProperty("问题答案")
    private String answer;
    @ApiModelProperty("问题状态")
    private Integer status;
    @ApiModelProperty("创建时间")
    private Date createTime;

}
