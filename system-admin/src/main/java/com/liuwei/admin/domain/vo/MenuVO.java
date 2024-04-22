package com.liuwei.admin.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(description = "权限实体")
public class MenuVO {
    @ApiModelProperty("权限ID")
    private Long menuId;

    @ApiModelProperty("权限名称")
    private String menuName;

    @ApiModelProperty("权限")
    private String authority;

    @ApiModelProperty("权限状态")
    private Integer status;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

}
