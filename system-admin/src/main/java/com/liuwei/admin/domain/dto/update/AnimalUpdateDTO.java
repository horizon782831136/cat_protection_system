package com.liuwei.admin.domain.dto.update;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("动物修改实体")
public class AnimalUpdateDTO {
    @ApiModelProperty(value = "动物id", required = true)
    private Long animalId;

    @ApiModelProperty(value = "动物名称")
    private String name;

    @ApiModelProperty(value = "动物图片id")
    private Long imageId;

    @ApiModelProperty(value = "动物性别")
    private Integer gender;

    @ApiModelProperty(value = "动物年龄")
    private Integer age;

    @ApiModelProperty(value = "动物分类id")
    private Long categoryId;

    @ApiModelProperty(value = "绝育")
    private Integer neutering;

    @ApiModelProperty(value = "动物描述")
    private String description;

    @ApiModelProperty(value = "登记时间")
    private Date registrationTime;

    @ApiModelProperty(value = "健康状况")
    private Integer health;

    @ApiModelProperty(value = "动物详情")
    private String detail;

    @ApiModelProperty(value = "状态")
    private Integer status;
}
