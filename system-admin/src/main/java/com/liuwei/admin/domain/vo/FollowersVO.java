package com.liuwei.admin.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@Data
@ApiModel
public class FollowersVO {
    @ApiModelProperty("关注ID")
    private Long followersId;

    @ApiModelProperty("关注者ID")
    private Long followerId;

    @ApiModelProperty("被关注者ID")
    private Long followeeId;

    @ApiModelProperty("关注状态")
    private Integer status;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;
}
