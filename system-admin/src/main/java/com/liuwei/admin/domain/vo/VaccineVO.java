package com.liuwei.admin.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(description = "疫苗实体")
public class VaccineVO {
    @ApiModelProperty("疫苗ID")
    private Long vaccineId;

    @ApiModelProperty("动物ID")
    private Long animalId;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("疫苗类型")
    private String type;

    @ApiModelProperty("接种时间")
    private Date inoculationTime;

    @ApiModelProperty("接种地址")
    private String address;

    @ApiModelProperty("疫苗有效期")
    private Integer periodOfValidity;

    @ApiModelProperty("疫苗路径")
    private String path;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;
}
