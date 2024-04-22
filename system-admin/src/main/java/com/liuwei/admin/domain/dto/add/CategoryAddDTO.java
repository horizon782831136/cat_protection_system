package com.liuwei.admin.domain.dto.add;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("类别添加实体")
public class CategoryAddDTO {
    @ApiModelProperty(value = "父级id", required = true)
    private Long parentId;

    @ApiModelProperty(value = "类别名称", required = true)
    private String name;

    @ApiModelProperty(value = "类别描述", required = true)
    private String description;

    @ApiModelProperty(value = "图片id", required = true)
    private Long imageId;

    @ApiModelProperty(value = "类别状态", required = true)
    private Integer status;
}
