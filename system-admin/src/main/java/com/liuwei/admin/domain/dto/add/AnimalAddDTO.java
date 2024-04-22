package com.liuwei.admin.domain.dto.add;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("动物添加实体")
public class AnimalAddDTO {
    @ApiModelProperty(value = "动物名称", required = true)
    private String name;

    @ApiModelProperty(value = "动物图片id", required = true)
    private Long imageId;

    @ApiModelProperty(value = "动物性别", required = true)
    private Integer gender;

    @ApiModelProperty(value = "动物年龄", required = true)
    private Integer age;

    @ApiModelProperty(value = "动物分类id", required = true)
    private Long categoryId;

    @ApiModelProperty(value = "绝育", required = true)
    private Integer neutering;

    @ApiModelProperty(value = "动物描述")
    private String description;

    @ApiModelProperty(value = "健康状况", required = true)
    private Integer health;

    @ApiModelProperty(value = "动物详情")
    private String detail;

    @ApiModelProperty(value = "状态", required = true)
    private Integer status;
}
