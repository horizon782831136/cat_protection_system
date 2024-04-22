package com.liuwei.user.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "主题实体")
public class ThemeVisitorVO {
    @ApiModelProperty("主题ID")
    private Long themeId;

    @ApiModelProperty("主题名称")
    private String themeName;

    @ApiModelProperty("主题CSS")
    private String themeCss;

    @ApiModelProperty("图片路径")
    private String path;

    @ApiModelProperty("选中")
    private Integer checked;

}
