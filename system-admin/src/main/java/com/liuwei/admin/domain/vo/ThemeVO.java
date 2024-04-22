package com.liuwei.admin.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(description = "主题实体")
public class ThemeVO {
    @ApiModelProperty("主题ID")
    private Long themeId;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("分享码")
    private String shareCode;

    @ApiModelProperty("主题名称")
    private String themeName;

    @ApiModelProperty("主题CSS")
    private String themeCss;

    @ApiModelProperty("主题图片ID")
    private Long imageId;

    @ApiModelProperty("选中")
    private Integer checked;

    @ApiModelProperty("主题状态")
    private Integer status;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;
}
