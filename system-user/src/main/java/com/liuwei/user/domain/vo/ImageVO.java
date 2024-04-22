package com.liuwei.user.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(description = "图片实体")

public class ImageVO {
    @ApiModelProperty("图片ID")
    private Long imageId;

    @ApiModelProperty("图片路径")
    private String path;

    @ApiModelProperty("原名")
    private String originalName;

    @ApiModelProperty("图片详情")
    private String detail;

    @ApiModelProperty("图片状态")
    private Integer status;

    @ApiModelProperty("创建时间")
    private Date createTime;

}
