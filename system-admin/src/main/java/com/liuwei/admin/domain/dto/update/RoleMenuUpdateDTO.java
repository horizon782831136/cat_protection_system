package com.liuwei.admin.domain.dto.update;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("角色权限修改实体")
public class RoleMenuUpdateDTO {
    @ApiModelProperty(value = "角色权限id", required = true)
    private Long id;
    @ApiModelProperty(value = "角色id")
    private Long roleId;
    @ApiModelProperty(value = "角色权限id")
    private Long menuId;
    @ApiModelProperty(value = "角色权限状态")
    private Integer status;
}
