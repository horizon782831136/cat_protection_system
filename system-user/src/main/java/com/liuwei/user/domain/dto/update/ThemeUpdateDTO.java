package com.liuwei.user.domain.dto.update;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("主题修改实体")
public class ThemeUpdateDTO {
    @ApiModelProperty(value = "主题id", required = true)
    private Long themeId;
    @ApiModelProperty(value = "用户id")
    private Long userId;
    @ApiModelProperty(value = "是否生成分享码", required = true)
    private Short generateShareCode;
    @ApiModelProperty(value = "主题名")
    private String themeName;
    @ApiModelProperty(value = "主题css")
    private String themeCss;
    @ApiModelProperty(value = "主题图片id")
    private Long imageId;
    @ApiModelProperty(value = "是否选中")
    private Integer checked;
    @ApiModelProperty(value = "状态")
    private Integer status;

}
