package com.liuwei.admin.domain.dto.update;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("用户修改实体")
public class UserUpdateDTO {
    @ApiModelProperty(value = "用户ID", required = true)
    private Long userId;
    @ApiModelProperty(value = "用户昵称")
    private String nickname;
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty("出生日期")
    private Date dateOfBirth;
    @ApiModelProperty(value = "性别")
    private Integer gender;
    @ApiModelProperty("密码")
    private String password;
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
    private Long avatar;
    @ApiModelProperty("详情")
    private String detail;
    @ApiModelProperty("状态")
    private Integer status;
}
