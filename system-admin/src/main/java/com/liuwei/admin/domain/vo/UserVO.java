package com.liuwei.admin.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(description = "用户实体")
public class UserVO {
    @ApiModelProperty("用户ID")
    private Long userId;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("用户昵称")
    private String nickname;
    @ApiModelProperty("出生日期")
    private Date dateOfBirth;
    @ApiModelProperty("性别")
    private Integer gender;
    @ApiModelProperty("电话号码")
    private String phoneNumber;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("身份证号")
    private String idNumber;
    @ApiModelProperty("电子邮件")
    private String email;
    @ApiModelProperty("职业")
    private String occupation;
    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty("签名")
    private String signature;
    @ApiModelProperty("角色")
    private Long role;
    @ApiModelProperty("头像")
    private String avatar;
    @ApiModelProperty("详情")
    private String detail;
    @ApiModelProperty("状态")
    private Integer status;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date updateTime;
}
