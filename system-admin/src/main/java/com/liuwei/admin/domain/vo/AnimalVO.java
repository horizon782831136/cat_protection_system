package com.liuwei.admin.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@Data
@ApiModel(description = "动物实体")
public class AnimalVO {
    @ApiModelProperty("动物ID")
    private Long animalId;

    @ApiModelProperty("动物名称")
    private String name;

    @ApiModelProperty("动物图片")
    private String avatar;

    @ApiModelProperty("动物性别")
    private Integer gender;

    @ApiModelProperty("动物年龄")
    private Integer age;

    @ApiModelProperty("动物种类ID")
    private Long categoryId;

    @ApiModelProperty("绝育")
    private Integer neutering;

    @ApiModelProperty("登记时间")
    private Date registrationTime;

    @ApiModelProperty("动物描述")
    private String description;

    @ApiModelProperty("动物详情")
    private String detail;

    @ApiModelProperty("健康")
    private String health;

    @ApiModelProperty("动物状态")
    private Integer status;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

}
