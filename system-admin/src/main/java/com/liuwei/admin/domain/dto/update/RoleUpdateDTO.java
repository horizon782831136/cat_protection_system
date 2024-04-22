package com.liuwei.admin.domain.dto.update;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("角色修改实体")
public class RoleUpdateDTO {
    @ApiModelProperty(value = "角色id", required = true)
    private Long roleId;
    @ApiModelProperty(value = "角色名称")
    private String roleName;
    @ApiModelProperty(value = "状态")
    private Integer status;
}
