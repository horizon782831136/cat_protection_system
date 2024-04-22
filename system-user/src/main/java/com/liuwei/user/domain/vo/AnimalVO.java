package com.liuwei.user.domain.vo;

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
    private String path;

    @ApiModelProperty("动物性别")
    private Integer gender;

    @ApiModelProperty("动物年龄")
    private Integer age;

    @ApiModelProperty("动物种类")
    private String category;

    @ApiModelProperty("绝育")
    private Integer neutering;

    @ApiModelProperty("登记时间")
    private Date registrationTime;

    @ApiModelProperty("动物描述")
    private String description;


    @ApiModelProperty("健康")
    private String health;

    @ApiModelProperty("动物状态")
    private Integer status;

    @ApiModelProperty("创建时间")
    private Date createTime;


}
