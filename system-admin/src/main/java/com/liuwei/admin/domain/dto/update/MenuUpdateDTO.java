package com.liuwei.admin.domain.dto.update;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("权限添加实体")
public class MenuUpdateDTO {
    @ApiModelProperty(value = "权限id", required = true)
    private Long menuId;
    @ApiModelProperty(value = "权限名称")
    private String menuName;
    @ApiModelProperty(value = "权限")
    private String authority;
    @ApiModelProperty(value = "状态")
    private Integer status;
}
