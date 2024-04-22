package com.liuwei.admin.domain.dto.update;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("推荐修改实体")
public class RecommendedUpdateDTO {
    @ApiModelProperty(value = "推荐id", required = true)
    private Long recommendedId;
    @ApiModelProperty(value = "用户id")
    private Long userId;
    @ApiModelProperty(value = "类别id")
    private Long categoryId;
    @ApiModelProperty(value = "系数")
    private Float coefficient;
    @ApiModelProperty(value = "得分")
    private Float score;
    @ApiModelProperty(value = "状态")
    private Integer status;
}
