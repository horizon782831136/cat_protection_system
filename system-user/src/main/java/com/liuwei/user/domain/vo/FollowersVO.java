package com.liuwei.user.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class FollowersVO {
    @ApiModelProperty("关注ID")
    private Long followersId;

    @ApiModelProperty("名称")
    private String nickname;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("签名")
    private String signature;

    @ApiModelProperty("关注者ID")
    private Long followerId;

    @ApiModelProperty("关注者头像")
    private String avatar;

    @ApiModelProperty("关注时间")
    private String createTime;

}
