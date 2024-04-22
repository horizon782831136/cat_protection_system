package com.liuwei.user.domain.dto.add;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("疫苗添加实体")
public class VaccineAddDTO {
    @ApiModelProperty(value = "动物id", required = true)
    private Long animalId;
    @ApiModelProperty(value = "用户id", required = true)
    private Long userId;
    @ApiModelProperty(value = "疫苗类型", required = true)
    private String type;
    @ApiModelProperty(value = "接种时间", required = true)
    private Date inoculationTime;
    @ApiModelProperty(value = "疫苗接种地址", required = true)
    private String address;
    @ApiModelProperty(value = "疫苗有效期", required = true)
    private Integer periodOfValidity;
    @ApiModelProperty(value = "凭证")
    private Long imageId;
}
