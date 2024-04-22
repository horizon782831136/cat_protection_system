package com.liuwei.admin.domain.dto.update;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("问题修改实体")
public class QuestionUpdateDTO {
    @ApiModelProperty(value = "问题id", required = true)
    private Long questionId;
    @ApiModelProperty(value = "用户id")
    private Long userId;
    @ApiModelProperty(value = "问题描述")
    private String description;
    @ApiModelProperty(value = "答案")
    private String answer;
    @ApiModelProperty(value = "状态")
    private Integer status;
}
