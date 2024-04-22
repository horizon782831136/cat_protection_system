package com.liuwei.user.domain.dto.update;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("疫苗修改实体")
public class VaccineUpdateDTO {
    @ApiModelProperty(value = "疫苗id", required = true)
    private Long vaccineId;
    @ApiModelProperty(value = "疫苗类型")
    private String type;
    @ApiModelProperty(value = "接种时间")
    private Date inoculationTime;
    @ApiModelProperty(value = "疫苗接种地址")
    private String address;
    @ApiModelProperty(value = "疫苗有效期")
    private Integer periodOfValidity;
    @ApiModelProperty(value = "凭证")
    private Long imageId;
    @ApiModelProperty(value = "状态")
    private Integer status;
}
