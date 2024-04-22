package com.liuwei.admin.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(description = "图片实体")

public class ImageVO {
    @ApiModelProperty("图片ID")
    private Long imageId;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("图片路径")
    private String path;

    @ApiModelProperty("图片名称")
    private String name;

    @ApiModelProperty("图片原始名称")
    private String originalName;

    @ApiModelProperty("图片详情")
    private String detail;

    @ApiModelProperty("图片状态")
    private Integer status;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;
}
