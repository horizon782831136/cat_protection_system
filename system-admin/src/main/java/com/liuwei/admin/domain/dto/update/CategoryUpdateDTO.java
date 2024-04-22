package com.liuwei.admin.domain.dto.update;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("类别修改实体")
public class CategoryUpdateDTO {
    @ApiModelProperty(value = "类别id", required = true)
    private Long categoryId;

    @ApiModelProperty(value = "父级id")
    private Long parentId;

    @ApiModelProperty(value = "类别名称")
    private String name;

    @ApiModelProperty(value = "类别描述")
    private String description;

    @ApiModelProperty(value = "图片id")
    private Long imageId;

    @ApiModelProperty(value = "类别状态")
    private Integer status;
}
