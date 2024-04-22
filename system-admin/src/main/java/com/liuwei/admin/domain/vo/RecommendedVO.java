package com.liuwei.admin.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(description = "推荐实体")
public class RecommendedVO {
    @ApiModelProperty("推荐ID")
    private Long recommendedId;
    @ApiModelProperty("用户ID")
    private Long userId;
    @ApiModelProperty("推荐分类ID")
    private Long categoryId;
    @ApiModelProperty("推荐系数" )
    private Float coefficient;
    @ApiModelProperty("推荐分数" )
    private Float score;
    @ApiModelProperty("推荐状态" )
    private Integer status;
    @ApiModelProperty("创建时间" )
    private  Date createTime;
    @ApiModelProperty("更新时间" )
    private Date updateTime;
}
