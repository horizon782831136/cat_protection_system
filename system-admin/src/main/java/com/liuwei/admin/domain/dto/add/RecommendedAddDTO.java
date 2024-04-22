package com.liuwei.admin.domain.dto.add;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("推荐添加实体")
public class RecommendedAddDTO {
    @ApiModelProperty(value = "用户id", required = true)
    private Long userId;
    @ApiModelProperty(value = "类别id", required = true)
    private Long categoryId;
    @ApiModelProperty(value = "系数", required = true)
    private Float coefficient;
    @ApiModelProperty(value = "得分", required = true)
    private Float score;
    @ApiModelProperty(value = "状态", required = true)
    private Integer status;
}
