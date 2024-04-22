package com.liuwei.admin.domain.dto.update;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("图片添加实体")
public class ImageUpdateDTO {
    @ApiModelProperty(value = "图片id", required = true)
    private Long imageId;
    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "图片状态")
    private Integer status;
}
