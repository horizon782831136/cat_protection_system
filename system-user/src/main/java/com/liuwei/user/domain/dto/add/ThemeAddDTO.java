package com.liuwei.user.domain.dto.add;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("主题添加实体")
public class ThemeAddDTO {
    @ApiModelProperty(value = "用户id", required = true)
    private Long userId;
    @ApiModelProperty(value = "主题名", required = true)
    private String themeName;
    @ApiModelProperty(value = "主题css")
    private String themeCss;
    @ApiModelProperty(value = "主题图片id")
    private Long imageId;
    @ApiModelProperty(value = "是否选中", required = true)
    private Integer checked;
    @ApiModelProperty(value = "是否生成分享码", required = true)
    private Short generateShareCode;
    @ApiModelProperty(value = "状态", required = true)
    private Integer status;

}
