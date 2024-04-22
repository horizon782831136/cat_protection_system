package com.liuwei.user.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "推荐实体")
public class RecommendedVO {
    @ApiModelProperty("推荐ID")
    private Long recommendedId;
    @ApiModelProperty("推荐分类ID")
    private Long categoryId;
    @ApiModelProperty("推荐系数" )
    private Float coefficient;
    @ApiModelProperty("推荐分数" )
    private Float score;

}
