package com.liuwei.user.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(description = "用户基本信息实体")
public class UserBasicVO {
    @ApiModelProperty("用户ID")
    private Long userId;
    @ApiModelProperty("名称")
    private String nickname;
    @ApiModelProperty("出生日期")
    private Date dateOfBirth;
    @ApiModelProperty("性别")
    private Integer gender;
    @ApiModelProperty("关注数")
    private Integer followerCount;
    @ApiModelProperty("粉丝数")
    private Integer followeeCount;
    @ApiModelProperty("职业")
    private String occupation;
    @ApiModelProperty("签名")
    private String signature;
    @ApiModelProperty("角色")
    private Long role;
    @ApiModelProperty("头像")
    private String avatar;
    @ApiModelProperty("状态")
    private Integer status;
    @ApiModelProperty("创建时间")
    private Date createTime;

}
