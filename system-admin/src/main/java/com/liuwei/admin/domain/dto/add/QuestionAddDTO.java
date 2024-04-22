package com.liuwei.admin.domain.dto.add;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("问题添加实体")
public class QuestionAddDTO {
    @ApiModelProperty(value = "用户id", required = true)
    private Long userId;
    @ApiModelProperty(value = "问题描述", required = true)
    private String description;
    @ApiModelProperty(value = "答案", required = true)
    private String answer;
    @ApiModelProperty(value = "状态", required = true)
    private Integer status;
}
