package com.liuwei.user.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.List;

@Data
@ApiOperation("根分类实体")
public class CategoryRootVO {
    @ApiModelProperty("分类ID")
    private Long categoryId;

    @ApiModelProperty("分类名称")
    private String name;
    @ApiModelProperty("子分类")
    private List<CategoryRootVO> children;
    @ApiModelProperty("分类描述")
    private String description;
    @ApiModelProperty("头像")
    private String path;
}
