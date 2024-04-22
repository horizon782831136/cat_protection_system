package com.liuwei.admin.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true) // 配置accessor风格
@ApiModel(description = "角色权限实体")
public class RoleMenuVO {
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("角色ID")
    private Long roleId;
    @ApiModelProperty("角色名称")
    private String roleName;
    @ApiModelProperty("权限ID")
    private Long menuId;
    @ApiModelProperty("权限名称")
    private String menuName;
    @ApiModelProperty("角色权限状态")
    private Integer status;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date updateTime;
}
