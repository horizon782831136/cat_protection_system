package com.liuwei.user.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@ApiModel(description = "分类实体")
@Accessors(chain = true)
public class CategoryVO {
    @ApiModelProperty("分类ID")
    private Long categoryId;

    @ApiModelProperty("父级分类ID")
    private Long parentId;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("分类描述")
    private String description;

    @ApiModelProperty("头像")
    private String path;


}
