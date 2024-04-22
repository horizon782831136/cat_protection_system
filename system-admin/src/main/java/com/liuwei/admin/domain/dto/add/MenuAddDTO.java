package com.liuwei.admin.domain.dto.add;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("权限添加实体")
public class MenuAddDTO {
    @ApiModelProperty(value = "权限名称", required = true)
    private String menuName;
    @ApiModelProperty(value = "权限", required = true)
    private String authority;
    @ApiModelProperty(value = "状态", required = true)
    private Integer status;
}
