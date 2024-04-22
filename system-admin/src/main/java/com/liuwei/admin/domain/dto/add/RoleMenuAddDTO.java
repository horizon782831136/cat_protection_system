package com.liuwei.admin.domain.dto.add;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("角色权限添加实体")
public class RoleMenuAddDTO {
    @ApiModelProperty(value = "角色id", required = true)
    private Long roleId;
    @ApiModelProperty(value = "角色权限id", required = true)
    private Long menuId;
    @ApiModelProperty(value = "角色权限状态", required = true)
    private Integer status;
}
